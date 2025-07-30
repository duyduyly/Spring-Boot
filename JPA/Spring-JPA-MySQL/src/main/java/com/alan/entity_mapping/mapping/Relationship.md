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
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
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
    @MapsId // Tells JPA to use the same ID as the User
    @JoinColumn(name = "id")
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
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
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

### Mapping Code
__Category.class__
```java
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

