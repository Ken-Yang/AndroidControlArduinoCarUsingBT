
// define the Pin
int iRightPin      = 8;
int iLeftPin       = 7;
int iRightSpeedPin = 6;
int iLeftSpeedPin  = 5;
int iLedPin        = 13;
int iSpeed         = 255;
void setup(){
  // define the PIN as OUTPUT
  pinMode(iRightPin      ,OUTPUT);
  pinMode(iLeftPin       ,OUTPUT);
  pinMode(iRightSpeedPin ,OUTPUT);
  pinMode(iLeftSpeedPin  ,OUTPUT);  
  pinMode(iLedPin        ,OUTPUT);  
  
  Serial.begin(115200);  // this value of baud rate depends on which bluetooth module you use.
}


void loop(){
  while (Serial.available()){
    char cCmd = Serial.read();
    if (cCmd =='w'){
      forward();
    }else if (cCmd == 's'){
      back();
    }else if (cCmd == 'a'){
      left(); 
    }else if (cCmd == 'd'){
      right(); 
    }else{
      pause();
    }
  }
}

void forward(){
  analogWrite (iRightSpeedPin,iSpeed);
  analogWrite (iLeftSpeedPin,iSpeed);
  digitalWrite(iRightPin,HIGH);
  digitalWrite(iLeftPin,HIGH);  
  digitalWrite(iLedPin,HIGH);
}
void back(){
  analogWrite (iRightSpeedPin,iSpeed);
  analogWrite (iLeftSpeedPin,iSpeed);
  digitalWrite(iRightPin,LOW);
  digitalWrite(iLeftPin,LOW);  
  digitalWrite(iLedPin,HIGH);
}
void left(){
  analogWrite (iRightSpeedPin,iSpeed);
  analogWrite (iLeftSpeedPin,iSpeed);
  digitalWrite(iRightPin,HIGH);
  digitalWrite(iLeftPin,LOW);    
  digitalWrite(iLedPin,HIGH);
}
void right(){
  analogWrite (iRightSpeedPin,iSpeed);
  analogWrite (iLeftSpeedPin,iSpeed);
  digitalWrite(iRightPin,LOW);
  digitalWrite(iLeftPin,HIGH);    
  digitalWrite(iLedPin,HIGH);
}
void pause(){
  analogWrite (iRightSpeedPin,0);
  analogWrite (iLeftSpeedPin,0);
  digitalWrite(iLedPin,LOW);
}
