#include <SPI.h>
#include <Ethernet.h>
#include <HCSR04.h>

UltraSonicDistanceSensor distanceSensor1(8, 7);
UltraSonicDistanceSensor distanceSensor2(6, 5);
int lengt=25000;


byte mac[] = {
  0x00, 0xAA, 0xBB, 0xCC, 0xDE, 0x02
};
IPAddress ip(192, 168, 254, 4);
EthernetClient client;

void setup() {
  Serial.begin(9600);
  while (!Serial) {
    ; 
  }
  
   Serial.println("start");
  Ethernet.begin(mac, ip);

  printIPAddress();
}

void loop() {
delay(2000);
      if (client.connect(IPAddress(192,168,254,1),80))
        {
          char buf[80];
           int sen1=distanceSensor1.measureDistanceCm();
           int sen2=distanceSensor2.measureDistanceCm();
           sprintf(buf, "GET /sensors?sens1=%d&sens2=%d HTTP/1.0",sen1,sen2);

    
  Serial.println(sen1);  Serial.println();
    Serial.println(sen2);
       //    delay(500);
           client.println(buf); // Отправляем GET запрос
       //    delay(100);
           client.println();
        //   delay(500);
           client.stop(); // Завершаем соединение
        }
        else
        {
        }

}

void printIPAddress()
{
  Serial.print("IP address: ");
  for (byte thisByte = 0; thisByte < 4; thisByte++) {
    Serial.print(Ethernet.localIP()[thisByte], DEC);
    Serial.print(".");
  }
  Serial.println();
}
