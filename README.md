# Welcome!

This is the 2019-2020 repository for FTC team 5143. All of our code is available for use by any team.

There are also multiple other respositories in this organization such as tools and simulators.

---

## Styleguide

### General

- `public ClassName {}`
- `public void methodName() {}`
- `public int variable_name = 5143`
- `public final int CONSTANT_NAME = 5143`

```java
public class ThisIs {
    public static main(String[] args) {
        if (The.Bracket) {
            Format;
         } else if (1 == 1) {
            You Should;
        } else {
            Use;
        }
    }
}
```

### Naming

- instances of `HardwareMap` are named `hwmap`
- instances of `Robot` are named `robot`
- instances of `Component` are the name of the component *class* in snake_case

### Commenting

```java
//// MOTORS ////
DcMotor some_motor;

//// SERVOS ////
Servo some_servo;

//// SENSORS ////
ColorSensor some_sensor;
```

```java
// description
// of component
// here

public class Extender extends Component {
    ...;
    // method description here
    public void extend() {
        ...;
    }
}
```

## Contributing

Contributions are welcome in the form of pull requests
