import socket
from crc.crc_emisor import *
import random

def apply_noise(data, error_probability):
    noisy_data = ''
    for bit in data:
        if random.random() < error_probability:
            noisy_bit = '1' if bit == '0' else '0'  # Flip the bit
            noisy_data += noisy_bit
        else:
            noisy_data += bit
    return noisy_data


def text_to_binary(text):
    return ''.join(format(ord(c), '08b') for c in text)

def send_data(data, port):
    server_socket = ('localhost', port)
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect(server_socket)
        s.sendall(data.encode())

if __name__ == "__main__":
    port = 12345 # any unused port can be used
    seguir = True
    while (seguir):
        print("=======================")
        print("1. Enviar mensaje")
        print("2. Realizar simulacion")
        print("3. Salir")
        scanner = int(input("Ingrese una opcion: ")) # convert input to int
        if scanner == 1:
            data = input("Ingresa el mensaje a enviar: ")
            data = text_to_binary(data)
            # print("data sin modificar: ", data)
            escogido = True
            while(escogido):
                print("=======================")
                print("Escoja el modelo que desea utilizar")
                print("1. CRC-32")
                print("2. Hamming")
                deci = int(input("Ingrese una opcion: "))
                if deci == 1:
                    data = inputText(data)
                    # print("data modificada ",data)
                    escogido = False
                elif deci == 2:
                    pass
                    escogido = False
                else:
                    print("Error escoja una opcion")
            error_probability = 0.01
            data = apply_noise(data, error_probability)

            send_data(data, port)
        elif scanner == 2:
            pass
        elif scanner == 3:
            print("Saliendo")
            seguir = False
        else:
            print("Error ingrese de nuevo")
