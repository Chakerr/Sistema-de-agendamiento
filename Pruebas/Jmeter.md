# Pruebas de concurrencia con JMeter

Se llevaron a cabo diversas pruebas para analizar el comportamiento de la plataforma web mediante múltiples peticiones concurrentes, específicamente 200. Estos son los resultados obtenidos:

## Registrar Estudiante
![Registrar Estudiante - Resultado 1][image1]  
![Registrar Estudiante - Resultado 2][image2]  
**Nota:** Solo aparece una request correcta porque solo se puede agregar un estudiante con el mismo identificador una vez.

---

## Correo Existente
![Correo Existente - Resultado 1][image3]  
![Correo Existente - Resultado 2][image4]

---

## Reservas Existentes
![Reservas Existentes - Resultado 1][image5]  
![Reservas Existentes - Resultado 2][image6]

---

## Login Estudiante
![Login Estudiante - Resultado 1][image7]  
![Login Estudiante - Resultado 2][image8]

---

## Obtener Inventario
![Obtener Inventario - Resultado 1][image9]  
![Obtener Inventario - Resultado 2][image10]

---

## Total de Reservas Activas
![Total de Reservas Activas - Resultado 1][image11]  
![Total de Reservas Activas - Resultado 2][image12]

---

## Total de Reservas Hechas
![Total de Reservas Hechas - Resultado 1][image13]  
![Total de Reservas Hechas - Resultado 2][image14]

---

## Reservas Activas por Estudiante
![Reservas Activas por Estudiante - Resultado 1][image15]  
![Reservas Activas por Estudiante - Resultado 2][image16]

---

## Reservas por Fecha
![Reservas por Fecha - Resultado 1][image17]  
![Reservas por Fecha - Resultado 2][image18]

---

## Crear Reservas
![Crear Reservas - Resultado 1][image19]  
![Crear Reservas - Resultado 2][image20]  
**Nota:** Solo aparece una request correcta porque solo se puede crear una reserva con el mismo ID para ese día.

---

## Cancelar Reservas
![Cancelar Reservas - Resultado 1][image21]  
![Cancelar Reservas - Resultado 2][image22]  
**Nota:** Solo aparece una request correcta porque solo se puede eliminar una vez la misma reserva.

---

[image1]: img/Imagen1.png
[image2]: img/Imagen2.png
[image3]: img/Imagen3.png
[image4]: img/Imagen4.png
[image5]: img/Imagen5.png
[image6]: img/Imagen6.png
[image7]: img/Imagen7.png
[image8]: img/Imagen8.png
[image9]: img/Imagen9.png
[image10]: img/Imagen10.png
[image11]: img/Imagen11.png
[image12]: img/Imagen12.png
[image13]: img/Imagen13.png
[image14]: img/Imagen14.png
[image15]: img/Imagen15.png
[image16]: img/Imagen16.png
[image17]: img/Imagen17.png
[image18]: img/Imagen18.png
[image19]: img/Imagen19.png
[image20]: img/Imagen20.png
[image21]: img/Imagen21.png
[image22]: img/Imagen22.png
