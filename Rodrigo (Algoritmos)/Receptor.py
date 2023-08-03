def calcRedundantBits(m):
    # Cálculo del número de bits redundantes necesario
    for i in range(m):
        if 2**i >= m + i + 1:
            return i

def posRedundantBits(data, r):
    # Colocar los bits redundantes en las posiciones que corresponden a las potencias de 2
    j = 0
    k = 1
    m = len(data)
    res = ''

    for i in range(1, m + r + 1):
        if i == 2**j:
            res = res + '0'
            j += 1
        else:
            res = res + data[-1 * k]
            k += 1

    return res[::-1]

def calcParityBits(arr, r):
    # Cálculo de los bits de paridad
    n = len(arr)
    for i in range(r):
        val = 0
        for j in range(1, n + 1):
            if j & (2**i) == (2**i):
                val = val ^ int(arr[-1 * j])
        arr = arr[:n - (2**i)] + str(val) + arr[n - (2**i) + 1:]
    return arr

def detectError(arr, nr):
    # Cálculo de los bits de paridad nuevamente
    n = len(arr)
    res = 0

    for i in range(nr):
        val = 0
        for j in range(1, n + 1):
            if j & (2**i) == (2**i):
                val = val ^ int(arr[-1 * j])
        res = res + val * (10**i)

    # Convertir binario a decimal
    return int(str(res), 2)

def ErrorCorrector(data_received):
    # Calcular el número de bits redundantes necesario
    m_received = len(data_received)
    r_received = calcRedundantBits(m_received)

    # Calcular las posiciones de los bits redundantes
    arr_received = posRedundantBits(data_received, r_received)

    # Calcular los bits de paridad
    arr_received = calcParityBits(arr_received, r_received)

    # Comparar el bit de paridad recibido con el calculado para detectar errores
    correction = detectError(data_received, r_received)

    # Mostrar el resultado
    if correction == 0:
        print("No se detectaron errores en la trama recibida.")
    else:
        print("Se detectó un error en la trama recibida en la posición:", len(data_received) - correction + 1, "desde la izquierda.")
        corrected_data = data_received[:len(data_received) - correction] + str(1 - int(data_received[len(data_received) - correction])) + data_received[len(data_received) - correction + 1:]
        print("Trama corregida sin el error:", corrected_data)
        return corrected_data

# Solicitar la trama al usuario
data_received = input("Ingrese la trama recibida (trama + bit de paridad modificado manualmente): ")

ErrorCorrector(data_received)



