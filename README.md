# Welcome!
This is the 2019-2020 repository for FTC team 5143. This readme, once completed, should contain: a formatting guide, a TODO, and whatever we should put in here for judges (I don't know).

---

## TODO

### General

- [ ] Drive correction
- [ ] Build-an-Autonomous
- [ ] autoStacking
- [ ] Output component (when designed)
- [ ] Skystone recognition
- [ ] Auto foundation pushing

### Readme:
- [ ] Add guide to telemetry
- [ ] Create OpMode formatting guide.

---

## Styleguide

### General

- `ClassName`
- `methodName()`
- `variable_name`
- `CONSTANT_NAME`

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

