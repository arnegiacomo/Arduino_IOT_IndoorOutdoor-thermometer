# Arduino IOT indoor and outdoor thermometer using REST API
[Arduino MKR 1000 WiFi](https://docs.arduino.cc/hardware/mkr-1000-wifi) based IOT device with indoor and outdoor 
[DS18B20](https://www.kjell.com/globalassets/mediaassets/745057_87081_manual_en.pdf?ref=C2D11F00F2)
temperature sensors, and [Spark](https://github.com/perwendel/spark) based REST API running on a 
[Raspberry Pi 4](https://www.raspberrypi.com/products/raspberry-pi-4-model-b/) that records the data into a
local PostgreSQL database.  First step in creating a smarter home!

### Progress
- [x] IOT device
- [x] Database
- [x] REST API
- [ ] Easily accessible web portal
- [ ] Display cool statistics
- [ ] More devices/sensors?

### Reading example
```
{
"ID": 241,
"TIME_STAMP": "Aug 4, 2022, 5:31:44 PM",
"INDOORTEMP": 24.25,
"OUTDOORTEMP": 19.06
}
```
### Arduino schematic
*~WIP~*
