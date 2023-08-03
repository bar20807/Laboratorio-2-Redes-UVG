def crc_calculation(trama_bits, crc):
    # Realizar el XOR entre los primeros len(crc) elementos de "trama_bits" y "crc"
    copia_trama = trama_bits.copy()
    calculate = []
    for x in range(len(crc)):
        calculate.append(copia_trama.pop(0))
    # print("copia_trama: ",copia_trama)
    # print("calculate: ",calculate)
    # print("==============")

    while (len(copia_trama)!=0):
        # print("calculate: ",calculate)
        for i in range(len(crc)):
            calculate[i] ^= crc[i]
        # print("calculate xor: ",calculate)
        seguir = True
        a = 0
        while(seguir):
            if calculate[a] == 0:
                calculate.pop(0)
            else:
                seguir = False
            
        agregar = True
        # print("copia_trama: ",copia_trama)
        while(agregar):
            if len(calculate) == len(crc):
                agregar = False
            else:
                if len(copia_trama) != 0:
                    calculate.append(copia_trama.pop(0))
                else:
                    agregar=False

    if len(crc) == len(calculate):
        for i in range(len(crc)):
            calculate[i] ^= crc[i]

    calculate = calculate[-(len(crc)-1):]

    # print("copia_trama: ",copia_trama)

    print("calculate to return: ",calculate)

    return calculate 

# Array de CRC (puedes usar crc_3 o crc_32 según lo desees)
crc_32 = [1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1]
crc_3 = [1, 0, 0, 1]

# Obtener la trama del usuario y convertirla en una lista de enteros
print("Ingrese la trama (solo 0s y 1s): ")
trama = input()
trama_bits = [int(bit) for bit in trama]

# Elegir el CRC deseado (crc_3 o crc_32)
crc = crc_32  # Cambiar a crc_32 si se quiere usar CRC-32

# Asegurarse de que la trama tenga la longitud adecuada para el CRC
trama_bits += [0] * (len(crc) - 1)
# print("trama_bits: ", trama_bits)

# Calcular los últimos bits del CRC según la longitud del CRC
crc_result = crc_calculation(trama_bits, crc)

# print("trama_bits: ",trama_bits)
# Agregar el resultado CRC a la trama original
trama_con_crc = trama_bits[:-(len(crc)-1)] + crc_result

# Imprimir la trama con CRC para enviar al receptor
print("Trama con CRC para enviar al receptor:")
result = ''.join(str(bit) for bit in trama_con_crc)
print(result)
