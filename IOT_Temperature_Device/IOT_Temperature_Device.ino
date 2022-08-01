#include <OneWire.h>
#include <DallasTemperature.h>
#include <SPI.h>
#include <WiFi101.h>
#include <WiFiClient.h>

using namespace std;

// Data wire is plugged into digital pin 6 on the Arduino
#define ONE_WIRE_BUS 6

// Setup a oneWire instance to communicate with any OneWire device
OneWire oneWire(ONE_WIRE_BUS);

// Pass oneWire reference to DallasTemperature library
DallasTemperature sensors(&oneWire);

int deviceCount = 0;
float tempC;

const int GREENLED = 0;
const int BLUELED = 1;
const int REDLED = 2;

//WiFi router setup
char ssid[] = ""; //network SSID (aka WiFi name)
char pass[] = ""; //network password
int status = WL_IDLE_STATUS;

//"192.168.50.179:8080"
IPAddress server(192,168,50,179); 
WiFiClient client;

void setup() {
  pinMode(GREENLED, OUTPUT);
  pinMode(REDLED, OUTPUT);
  pinMode(BLUELED, OUTPUT);

  sensors.begin();  // Start up the library

  Serial.begin(9600);

  while (!Serial) {
    ; // wait for serial port to connect.
  }

  // locate devices on the bus
  Serial.println("Locating temperature sensors...");
  Serial.print("Found ");
  deviceCount = sensors.getDeviceCount();
  Serial.print(deviceCount, DEC);
  Serial.println(" devices.");
  Serial.println();

  wifiSetup();
}

float indoorTemp = -100;
float outdoorTemp = -100;

void loop() {
  
  digitalWrite(BLUELED, HIGH);

  // Send command to all the sensors for temperature conversion
  sensors.requestTemperatures();

  Serial.println();

  Serial.print("Indoor : ");
  indoorTemp = sensors.getTempCByIndex(1);
  Serial.print(indoorTemp);
  Serial.print("°C");

  Serial.println();

  Serial.print("Outdoor : ");
  outdoorTemp = sensors.getTempCByIndex(0);
  Serial.print(outdoorTemp);
  Serial.print("°C");

  Serial.println();

  Serial.println("----------------------");


  Serial.println("\nStarting connection to server...");
  // if you get a connection, report back via serial:

  if (client.connect(server, 8080)) {
    Serial.println("Connected to server!");

    // Make a HTTP request:
    String str = "PUT /temperaturelogger/log/?indoorTemp=";
    str +=  String(indoorTemp);
    str += "&outdoorTemp=";
    str += String(outdoorTemp);
    str += " HTTP/1.1";

    client.println(str);
    client.println("Host: 192.168.50.179:8080");
    client.println("Connection: close");
    client.println();

  }
  
  delay(10000);

}

void wifiSetup() {
  // Check for the presence of the shield
  Serial.print("WiFi101 shield: ");
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("NOT PRESENT");
    return; // don't continue
  }
  Serial.println("DETECTED");
  // attempt to connect to Wifi network:
  while ( status != WL_CONNECTED) {
    Serial.print("Attempting to connect to Network named: ");
    Serial.println(ssid);                   // print the network name (SSID);
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(ssid, pass);
    // wait 10 seconds for connection:
    delay(10000);
  }
  printWifiStatus();
}

void printWifiStatus() {

  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address:

  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}
