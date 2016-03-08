#!/usr/bin/python3

# Definition d'un client reseau rudimentaire
# Ce client dialogue avec un serveur ad hoc

import socket, sys
from script.led.SimulLed import SimulLed


HOST = '127.0.0.1'
PORT = 50000
NOM = "RP2"

    
# 1) creation du socket :
mySocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 2) envoi d'une requete de connexion au serveur :
try:
    mySocket.connect((HOST, PORT))
except socket.error:
    print("La connexion a echoue.")
    sys.exit()    
print("Connexion etablie avec le serveur.")
    
# 3) Dialogue avec le serveur :



message = str.encode("%s" % (NOM))
mySocket.send(message)

msgServeur = bytes.decode(mySocket.recv(1024))
print(msgServeur)


led = ""
ledHisto = ""
while 1:
    try:
        led = SimulLed.getLed()
        if led!=ledHisto:
            print("la led allume:", led)
            mySocket.send(str.encode(led))
        ledHisto = led
        
    except KeyboardInterrupt:
        mySocket.send(str.encode("FIN"))
        print("Connexion interrompue.")
        mySocket.close()            
        break
 





    