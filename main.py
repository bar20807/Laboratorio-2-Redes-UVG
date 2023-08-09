import socket
import string
from crc.crc_emisor import *
import random
import matplotlib.pyplot as plt
import numpy as np

def apply_noise(data, error_probability):
    noisy_data = ''
    for bit in data:
        if random.random() < error_probability:
            noisy_bit = '1' if bit == '0' else '0'  # Flip the bit
            noisy_data += noisy_bit
        else:
            noisy_data += bit
    return noisy_data

def generate_example_text():
    lengths = np.random.randint(1, 21, size=1000)  # Generate 1000 random lengths between 1 and 20
    example_texts = []
    for length in lengths:  
        example_text = ''.join(random.choice(string.ascii_lowercase) for _ in range(length))
        example_texts.append(example_text)
    return example_texts


def text_to_binary(text):
    return ''.join(format(ord(c), '08b') for c in text)

def send_data(data, port, deci, scanner):
    server_socket = ('localhost', port)
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect(server_socket)
        s.sendall(f"{data},{deci},{scanner}".encode())

if __name__ == "__main__":
    port = 12345 # any unused port can be used
    seguir = True
    while seguir:
        print("=======================")
        print("1. Enviar mensaje")
        print("2. Realizar simulacion")
        print("3. Salir")
        scanner = int(input("Ingrese una opcion: ")) # convert input to int
        if scanner == 1:
            data = input("Ingresa el mensaje a enviar: ")
            data = text_to_binary(data)
            escogido = True
            while escogido:
                print("=======================")
                print("Escoja el modelo que desea utilizar")
                print("1. CRC-32")
                print("2. Hamming")
                deci = int(input("Ingrese una opcion: "))
                if deci == 1:
                    data = inputText(data)
                    escogido = False
                elif deci == 2:
                    pass
                    escogido = False
                else:
                    print("Error escoja una opcion")
            error_probability = 0.01
            data = apply_noise(data, error_probability)

            send_data(data, port, deci,scanner)
        elif scanner == 2:
            example_texts = generate_example_text()
            escogido = True
            while escogido:
                print("=======================")
                print("Escoja el modelo que desea utilizar")
                print("1. CRC-32")
                print("2. Hamming")
                deci = int(input("Ingrese una opcion: "))
                if deci == 1 or deci == 2:
                    escogido = False
                else:
                    print("Error escoja una opcion")
            for text in example_texts:
                print("text: ",text)
                data = text_to_binary(text)
                
                if deci == 1:
                    data = inputText(data)
                    escogido = False
                elif deci == 2:
                    pass
                    escogido = False

                error_probability = 0.01
                data = apply_noise(data, error_probability)
                send_data(data, port, deci, scanner)


        elif scanner == 3:
            print("Saliendo")
            seguir = False
        else:
            print("Error ingrese de nuevo")
