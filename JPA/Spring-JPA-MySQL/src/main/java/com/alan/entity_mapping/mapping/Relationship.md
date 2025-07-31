# Relationship Table

- [**One To One**](#one-to-one-1-1)
  - [*Solution 1 easy to Save*](#solution-1-easy-to-save-)
  - [*Solution 2: Use MapIds*](#solution-2-use-mapids)
  - [*Difference Two Solution And When Use*](#difference-between-two-solution-and-when-use)
  - [*Test*](#test-one-to-one)
- [**One To Many**](#one-to-many-1-n)
  - [*Mapping Code*](#mapping-code)
  - [*Test*](#test-one-to-many)
- [**Many To Many**](#many-to-many-n-navoid)
  - [*Solution 1*](#manytomany-solution-1-complex-to-scale-up-table-and-query)
  - [*Solution 1 Test*](#manytomany-solution-1-test)
  - [*Solution 2*](#manytomany-solution-2)
  - [*Solution 2 Test*](#test-manytomany-solution-2)

#

## One To One (1-1)
- Advice, should `fetch = FetchType.EAGER` because One User Only One Profile (can get when Query User)

### Solution 1: easy to Save: 
- Save profile from User
- Create Profile and Set into User and Save (so easy)

__User.class__
```java
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // can eager because one User is Only one Profile
    @JoinColumn(name = "profile_id", referencedColumnName = "id") //Creates a foreign key column profile_id in the user table that refers to the id column in the profile table.
    private Profile profile;
}
```

__Profile.class__
```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PROFILE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String phone;

    @OneToOne(mappedBy = "profile")
    private User user;
}
```
#

### Solution 2: Use `MapIds`
- use `MapsId` Id of Profile Will Share With User, id of Profile same Id with User
- Save: Must Save User First To Generate Id, and then set for Profile (if not will throw error)


```java
    @Id
    private Long id;  // Shared primary key with User

    @OneToOne()
    @MapsId() // Reuses the Profile's primary key (id) as the foreign key to User. It means Profile's ID is the same as User's ID.
    @JoinColumn(name = "id") //The foreign key column is id, and it also acts as the primary key of Profile.
    private User user;
```

__User.class__
```java
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // can eager because one User is Only one Profile
    @JoinColumn(name = "profile_id", referencedColumnName = "id") //Creates a foreign key column profile_id in the user table that refers to the id column in the profile table.
    private Profile profile;
}
```

__Profile.class__
```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PROFILE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Profile {
    @Id
    private Long id;  // Shared primary key with User

    private String fullName;
    private String phone;

    @OneToOne()
    @MapsId // Tells JPA to use the same ID as the User
    @JoinColumn(name = "id")
    private User user;
}
```

**Create Method**
- Save From Profile
```java
 public User createUser() {
        long count = userRepository.count();
        
        User user = new User();
        user.setUsername(count ==  0 ? "Alan" : "Alan"+count);
        User save = userRepository.save(user); // need save User to Generate id

        Profile profile = new Profile();
        profile.setFullName("Walk");
        profile.setPhone("123456789");
        profile.setUser(save); //and then take id of User to set id for Profile
        user.setProfile(profile);

        profileRepository.save(profile);
        return user;// Profile is also saved automatically
    }
```

### Difference Between Two Solution And When Use
- **🧠 Conceptual Differences**:

| Feature                  | `@OneToOne` (normal)                    | `@OneToOne` + `@MapsId`                          |
|--------------------------|-----------------------------------------|--------------------------------------------------|
| **Primary key strategy** | Each entity has its **own** primary key | One entity **shares** the primary key of another |
| **ID Generation**        | Separate `@GeneratedValue` per entity   | No ID generation; uses parent's ID (`@MapsId`)   |
| **Foreign Key (FK)**     | One entity has FK to the other          | FK **is also the PK** of the child entity        |
| **Use case**             | General-purpose one-to-one relationship | When child’s ID **must be the same** as parent   |
| **Flexibility**          | More flexible, independent entities     | Tightly coupled by design                        |

<br/>

- **🎯 When to Use What?**

| Use case                                        | Choose               |
|-------------------------------------------------|----------------------|
| You want tight binding, same ID in both tables  | ✅ `@MapsId`          |
| You want flexible separate entities             | ✅ Normal `@OneToOne` |
| You want to enforce strict lifecycle dependency | ✅ `@MapsId`          |
| You may reuse profiles or have optional linkage | ✅ Normal `@OneToOne` |


### Test One To One

- Controller: [UserController.java](one_to_one/test/UserController.java)

- Get:
```text
http://localhost:9999/api/one-to-one/get
```
```text
[{"username":"Alan1","fullName":"Walk","phone":"123456789"},
{"username":"alan","fullName":"Jessie Klein Updated","phone":"278-128-6705 x312"}]
```

#

- Create
```text
http://localhost:9999/api/one-to-one/create?username=alan10
```
```text
{"username":"alan10","fullName":"Tristan Fritsch","phone":"284-311-0875"} //create Success
```

#

- Update
```text
http://localhost:9999/api/one-to-one/update?username=alan10
```
```text
{"username":"alan10","fullName":"Tristan Fritsch Updated","phone":"284-311-0875"}
```

#

- Delete
```text
http://localhost:9999/api/one-to-one/delete?username=alan10
```
```text
Delete Success, Let go to Api Get to check!
```

---------------------------
<br/>

## One to Many (1-n)
- A Category Will have a lot of Product
- Can Create Product From Category, Just `Set Category in Product` and `set List product for category` and s`ave category`
 
| Annotation        | Purpose                                                     |
|-------------------|-------------------------------------------------------------|
| `@OneToMany`      | Defines the one-side of a one-to-many relationship.         |
| `mappedBy`        | Points to the owning side’s field (`Product.category`).     |
| `@ManyToOne`      | Defines the many-side pointing to one category.             |
| `@JoinColumn`     | Specifies the actual foreign key column name.               |


### Mapping Code
__Category.class__
```java
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "CATEGORY")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //Declares a one-to-many relationship (1 category → many products).
    //tells JPA: “The category field in the Product class owns the relationship.” This avoids an extra join table.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
```

__Product.class__
```java
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PRODUCT")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    // `@ManyToOne`	Many products belong to one category.
    //`@JoinColumn(name = "category_id")` Defines the foreign key in the product table (category_id).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // Foreign Key column in Product
    private Category category;
}
```

__Create Category and Product:__
```java
  @GetMapping("/create")
    public String create(@RequestParam String categoryName, @RequestParam List<String> productNameList) {
        Category category = new Category();
        category.setName(categoryName);
        List<Product> productList = productNameList.stream().map(pd -> new Product(pd, category)).toList();
        category.setProducts(productList);

        Category save = categoryRepository.save(category);
        return jsonUtils.convertToJson(new ProductDto(save));
    }
```
### Test One To Many
- Controller: [ProductController.java](one_to_many/test/ProductController.java)
- Get Api:
```text
http://localhost:9999/api/one-to-many/get
```
```text
[{"category":"Phone","product_name_list":["Samsung s24","Samsung s23","Samsung s22"]},{"category":"Phone","product_name_list":["Samsung s24","Samsung s23","Samsung s22"]}]
```

#

- Create Category and Product
```text
http://localhost:9999/api/one-to-many/create?categoryName=Phone&productNameList=Samsung%20s22&productNameList=Samsung%20s23&productNameList=Samsung%20s24
```
```text
{"category":"Phone","product_name_list":["Samsung s22","Samsung s23","Samsung s24"]}
```


## Many to Many (n-n)(avoid)


__Note:__

| Principle                         | Why It Matters                                      |
|-----------------------------------|-----------------------------------------------------|
| Use join entity                   | Add custom fields and queries                       |
| Use composite key (`@EmbeddedId`) | Precise mapping and uniqueness                      |
| Avoid direct `@ManyToMany`        | Lack of flexibility and hard to query               |
| Query using repositories          | Avoid lazy loading problems and improve performance |
| Control cascade                   | Prevent unwanted deletions or duplicate inserts     |
| Start with uni-directional        | Simpler and less error-prone                        |


#
### ManyToMany Solution 1: (Complex to Scale up table and query)
__Student.class__
```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Student(String name, Set<Course> course) {
        this.name = name;
        this.courses = course;
    }

  //joinColumns =  @JoinColumn(name = "student_id"),  this entity, This refers to the column in the join table (student_course) that maps to this entity’s primary key., Since this is inside the Student entity, student_id maps to Student.id.
  //inverseJoinColumns = @JoinColumn(name = "course_id")
  //This refers to the column in the join table that maps to the other entity — Course.id.
  @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),          
            inverseJoinColumns = @JoinColumn(name = "course_id")     // other entity
    )
    private Set<Course> courses = new HashSet<>();
}

```

__Course.class__
```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COURSE")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    public Course(String title) {
        this.title = title;
    }

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();
}
```

__Save method:__
```java
 @GetMapping("/create")
    public String create(@RequestParam String studentName, @RequestParam List<String> courseTitleList) {
        Set<Course> courseSet = courseTitleList.stream().map(Course::new).collect(Collectors.toSet());
        courseRepository.saveAll(courseSet);
        Student student = new Student(studentName, courseSet);
        Student save = studentRepository.save(student);
        return jsonUtils.convertToJson(new StudentDto(save));
    }
```

#
### ManyToMany Solution 1 Test

- Get
```text
http://localhost:9999/api/many-to-many/get
```
```text
[{"student_name":"Alan","course_name_List":["Java","AWS","Python"]}] ["AWS","Java","Python"]
```

- Create
```text
http://localhost:9999/api/many-to-many/create?studentName=Alan&courseTitleList=Python&courseTitleList=Java&courseTitleList=AWS
```
```text
{"student_name":"Alan","course_name_List":["Java","AWS","Python"]}
```

#

###  ManyToMany Solution 2:
- less complex from `many-to-many` to `one-to-many`  
  - You can store extra data (grade, enrollDate, status)
  - You can write complex queries (e.g., filter by date)
  - Better normalization and SQL JOIN control
- `MapsId` and [StudentCourseId.java](many_to_many/solution_2/StudentCourseId.java)  create relationship to course and can query by id of both
  - ` @EmbeddedId` or `@IdClass` for Composite Keys
- Always Adding Index (`indexes = @Index(name = "idx_student2_course2", columnList = "student2_id, course2_id")`) to `speed up` query when `search`, `filter`, `join` or `sort`

[Course2.java](many_to_many/solution_2/Course2.java)
```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COURSE2")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Course2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public Course2(String title) {
        this.title = title;
    }
}
```

[Student2.java](many_to_many/solution_2/Student2.java)
```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "STUDENT2")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Student2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Student2(String studentName) {
        this.name = studentName;
    }
}
```

[Student2Course2.java](many_to_many/solution_2/Student2Course2.java)
```java
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Entity
@Table(name = "STUDENT2_COURSE2", indexes = @Index(name = "idx_student2_course2", columnList = "student2_id, course2_id"))
@EqualsAndHashCode(exclude = {"student2", "course2"})
@ToString(exclude = {"student2", "course2"})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Student2Course2 {

    @EmbeddedId
    private StudentCourseId id = new StudentCourseId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId") //Mapping with key in StudentCourseId
    private Student2 student2;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId") //Mapping with key in StudentCourseId
    private Course2 course2;

    @Builder.Default
    private LocalDate enrollDate = LocalDate.now(); // Optional custom field

    public Student2Course2(Student2 student2, Course2 course2) {
        this.student2 = student2;
        this.course2 = course2;
        this.id = new StudentCourseId(student2.getId(), course2.getId());
    }
}
```

[StudentCourseId.java](many_to_many/solution_2/StudentCourseId.java)
- Save key of two table to easily query.
- Just find by key can get Student or CourseId
```java
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseId implements Serializable {
    private Long studentId;
    private Long courseId;
}
```

__Create Method__
```java
   @GetMapping("/create2")
    public String create(@RequestParam String studentName, @RequestParam List<String> courseTitleList) {
        Student2 student = new Student2(studentName);
        studentRepository2.save(student);

        Set<Course2> courseSet = courseTitleList.stream().map(Course2::new).collect(Collectors.toSet());
        courseRepository2.saveAll(courseSet);

        Set<Student2Course2> enroll = courseSet.stream().map(course2 -> new Student2Course2(student, course2)).collect(Collectors.toSet());
        List<Student2Course2> student2Course2s = student2Course2Repository2.saveAll(enroll);
        return jsonUtils.convertToJson(this.getStudentMapByStudentId(student2Course2s));
    }
```

### Test ManyToMany Solution 2:
- Get:
```text
http://localhost:9999/api/many-to-many/get2
```
```text
{"Alan6":["Java","AWS","Python"],"Alan4":["Java","AWS","Python"],"Alan5":["Java","AWS","Python"],"Alan2":["Java","AWS","Python"],"Alan3":["Java","AWS","Python"],"Alan1":["Java","AWS","Python"]}
```

- Create:
```text
http://localhost:9999/api/many-to-many/create2?studentName=Alan&courseTitleList=Python&courseTitleList=Java&courseTitleList=AWS
```
```text
{"Alan":["Java","AWS","Python"]}
```