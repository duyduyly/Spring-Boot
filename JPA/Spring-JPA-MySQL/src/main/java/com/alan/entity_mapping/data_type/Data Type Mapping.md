# Data Type Mapping
- [**Basic Data Type**](#basic-data-type)
- [**Date Time**](#date-time)
- [**Big Number And Enum**](#big-number-and-enum)
  - [*Big Number*](#big-number)
  - [*Enum*](#about-enum-type)
- [**Lobs Large Object**](#lobs-large-objects)
- [**Embedded Types**](#embedded-types)

#
## Basic Data Type
| Java Type            | SQL Type (Common) | Notes                              |
|----------------------|-------------------|------------------------------------|
| `String`             | `VARCHAR`, `TEXT` | Use `@Column(length = n)` to limit |
| `int`, `Integer`     | `INTEGER`         | Primitive vs. wrapper type         |
| `long`, `Long`       | `BIGINT`          |                                    |
| `boolean`, `Boolean` | `BOOLEAN`, `BIT`  |                                    |
| `double`, `Double`   | `DOUBLE`          |                                    |
| `float`, `Float`     | `FLOAT`           |                                    |
| `short`, `Short`     | `SMALLINT`        |                                    |
| `byte`, `Byte`       | `TINYINT`         |                                    |
| `char`, `Character`  | `CHAR(1)`         |                                    |

----------------------
<br/>


## Date Time
| Java Type                  | SQL Type                   | Annotation              | Notes                                    |
|----------------------------|----------------------------|-------------------------|------------------------------------------|
| `java.util.Date`           | `TIMESTAMP`                | `@Temporal(TIMESTAMP)`  | Legacy, not recommended for new projects |
| `java.sql.Date`            | `DATE`                     |                         | Only date part                           |
| `java.time.LocalDate`      | `DATE`                     | No need for `@Temporal` | Recommended                              |
| `java.time.LocalTime`      | `TIME`                     |                         | Recommended                              |
| `java.time.LocalDateTime`  | `TIMESTAMP`                |                         | Recommended                              |
| `java.time.OffsetDateTime` | `TIMESTAMP WITH TIME ZONE` |                         | Time zone support                        |

- requires `@Temporal`

```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Temporal(TemporalType.DATE)
private Date dateOnly;

@Temporal(TemporalType.TIME)
private Date timeOnly;

@Temporal(TemporalType.TIMESTAMP)
private Date fullDateTime;

private LocalDate localDate; //only date
private LocalTime localTime; // only time
private LocalDateTime timestamp; //timestamp
```

----------------------
<br/>


## Big Number And Enum
| Java Type              | SQL Type           | Notes                              |
|------------------------|--------------------|------------------------------------|
| `java.math.BigDecimal` | `DECIMAL`          | Best for money, precision values   |
| `java.math.BigInteger` | `NUMERIC`          | Rare, used for very large integers |
| `enum`                 | `VARCHAR` or `INT` | Use `@Enumerated(EnumType.STRING)` |

### Big Number
#### Limit number 
- `@Column(precision = X, scale = Y)`
  - `precision` = total number of digits
  - `scale` = number of digits after the decimal point

| Java Type    | SQL Type Generated          | Annotation                  |
|--------------|-----------------------------|-----------------------------|
| `BigDecimal` | `DECIMAL(precision, scale)` | `@Column(precision, scale)` |
| `Double`     | `DOUBLE` or `FLOAT`         | optionally `@Column`        |
| `BigInteger` | `NUMERIC` or `DECIMAL`      | `@Column(precision = X)`    |

```java

//Meaning: max 10 digits total, with 2 digits after the decimal point
@Column(precision = 10, scale = 2)
private BigDecimal price; //DECIMAL(10, 2) 
```

#
### About Enum Type

```java
import com.alan.Enums.FieldTypeEnums;
import jakarta.persistence.AttributeConverter;

    public class FieldTypeConverter implements AttributeConverter<FieldTypeEnums, Integer> {
        @Override
        public Integer convertToDatabaseColumn(FieldTypeEnums attribute) {
            return null;
        }
    
        @Override
        public FieldTypeEnums convertToEntityAttribute(Integer dbData) {
            return null;
        }
    }
    
    @Column
    @Convert(converter = FieldTypeConverter.class) // @Annotation to convert From String Enum To Int Enums when Data saved in Database
    private FieldTypeEnums fieldType;

    @Enumerated(EnumType.STRING)
    private Role role;
```

----------------------
<br/>

## LOBs (Large Objects)
| Java Type | SQL Type | Annotation | Notes                             |
|-----------|----------|------------|-----------------------------------|
| `String`  | `CLOB`   | `@Lob`     | Use for large text                |
| `byte[]`  | `BLOB`   | `@Lob`     | Use for binary data (e.g. images) |

```java
    import jakarta.persistence.Lob;

    @Lob
    private String message;

    @Lob
    private byte[] image;
```


----------------------
<br/>


## Embedded Types
-  Maps embedded objects as part of the main entity.

```java
@Embeddable
public class Address {
    private String street;
    private String city;
}
```
```java
@Embedded
private Address address;
```