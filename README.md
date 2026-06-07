# RainSense

An Android app for exploring and visualising embedded device sensors in real time. Built as part of a Mobile Development course using Java.

---


---

## Features

| Feature | Description |
|---|---|
| Sensor List | All available sensors with technical info |
| Temperature | Live ambient temperature graph |
| Humidity | Relative humidity graph |
| Proximity | Near/far object detection |
| Magnetic Field | Field magnitude over time |
| Accelerometer | X, Y, Z axes and magnitude |
| Gravity | Gravitational component per axis |
| Gyroscope | Rotation rate in rad/s |
| Step Counter | Steps since reboot and current session |
| Compass | Heading in degrees (N / S / E / W) |
| Activity Recognition | Detects walking, jumping, stationary state |

---

## Tech Stack

| Technology | Role |
|---|---|
| SensorManager | Access to Android hardware sensors |
| SensorEventListener | Real-time sensor event callbacks |
| Fragment | Modular UI organisation |
| Navigation Component | Fragment navigation |
| Canvas / Path | Custom line chart rendering |
| Handler | Simulated data for unavailable sensors |

---

## Sensor Details Displayed

For each sensor in the list:

- **Id** — unique identifier
- **Name** — sensor name
- **Vendor** — manufacturer
- **Version** — sensor version
- **Type** — human-readable type label
- **Int Type** — integer type constant
- **Resolution** — smallest detectable change
- **Power** — consumption in mA
- **Maximum Range** — max measurable value
- **Min Delay** — minimum sampling interval in µs

---

## Getting Started

```bash
git clone https://github.com/rain/rainsense.git
```

Open in **Android Studio**, sync Gradle, then hit **Run**.

> Min SDK: 24 — Target SDK: 34 — Java 17

---

## Test Results

| Feature | Status |
|---|---|
| Sensor list | ✅ |
| Temperature (simulated) | ✅ |
| Humidity (simulated) | ✅ |
| Proximity | ✅ |
| Magnetic field | ✅ |
| Accelerometer | ✅ |
| Gravity | ✅ |
| Gyroscope | ✅ |
| Step counter | ✅ |
| Compass | ✅ |
| Activity recognition | ✅ |

---

## Author

**rain** · yousraazarri@gmail.com
