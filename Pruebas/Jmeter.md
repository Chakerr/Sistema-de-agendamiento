**Pruebas de concurrencia con JMeter**  
Para probar la eficiencia de la plataforma web se llevaron a cabo distintas pruebas para analizar el comportamiento de la misma mediante varias peticiones al mismo tiempo, específicamente 200, estos fueron los resultados dados por JMeter.  

*Registrar Estudiante*  
![][image1]  
![][image2]  
Nota: Solo aparece bien un request porque solo se puede agregar un estudiante igual  

**Correo Existente**  
![][image3]  
![][image4]  

**RESERVAS EXISTENTES**  
![][image5]  
![][image6]  

**LOGIN ESTUDIANTE**  
![][image7]  
![][image8]  

**OBTENER INVENTARIO**  
![][image9]  
![][image10]  

**TOTAL DE RESERVAS ACTIVAS**  
![][image11]  
![][image12]  

**TOTAL DE RESERVAS HECHAS**  
![][image13]  
![][image14]  

**RESERVAS ACTIVAS POR ESTUDIANTE**  
![][image15]  
![][image16]  

**RESERVAS POR FECHA**  
![][image17]  
![][image18]  

**CREAR RESERVAS**  
![][image19]  
![][image20]  
Nota: solo aparece una request correcta debido a que solo se puede crear una reserva con el mismo id para ese día.  

**CANCELAR RESERVAS**  
![][image21]  
![][image22]  
Nota: solo aparece una request correcta debido a que solo se puede eliminar una vez aquella reserva.

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
