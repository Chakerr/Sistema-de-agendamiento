**SCRIPT CARRERAS** 

INSERT INTO public.carreras(  
	id, carrera)  
	VALUES   
	(1, 'Ingeniería de Sistemas'),  
	(2, 'Ingeniería de Telecomunicaciones'),  
	(3, 'Ingeniería Mecatrónica'),  
	(4, 'Ingeniería Financiera'),  
(5, 'Estudiante no perteneciente a la Universidad');

**SCRIPT LABORATORISTA**  
INSERT INTO public.laboratorista(  
	id\_codigo, contrasena, correo)  
	VALUES   
(1, 'Laboratorista2024.', '[laboratoristaupc@gmail.com](mailto:laboratoristaupc@gmail.com)'),  
	(2, 'JefeLaboratistas2024.', '[jefelaboratistasupc@gmail.com](mailto:jefelaboratistasupc@gmail.com)'),  
(3, 'GerenteLaboratistas2024.', '[gerentelaboratistasupc@gmail.com](mailto:gerentelaboratistasupc@gmail.com)'),  
(4, 'AdminsReservas2024.', 'adreservasupc[@gmail.com](mailto:administradoresreservasupc@gmail.com)');

**SCRIPT HORARIO REDES** 

DO $$  
DECLARE  
    fecha DATE;  
    dia INT;  
    hora INT;  
BEGIN  
    FOR dia IN 0..6 LOOP  \-- Ciclo para los di­as de la semana (0 \= Lunes, 6 \= SÃ¡bado)  
        fecha := CURRENT\_DATE \- (EXTRACT(DOW FROM CURRENT\_DATE)::int) \+ dia \+ 1;  \-- Calcula la fecha del dÃ­a actual  
        FOR hora IN 6..20 LOOP  \-- Ciclo para las horas (de 06:00 a 20:00)  
            INSERT INTO horarios\_redes (id, capacidad\_parcial, fecha, hora) VALUES ((dia \* 15\) \+ (hora \- 6 \+ 1), 0, fecha, (hora || ':00')::time);  
        END LOOP;  
    END LOOP;  
END $$;

**SCRIPT INVENTARIO**

INSERT INTO inventario (id\_inventario, cantidad, equipo) VALUES  
    (1, 2, 'Kit tester Link Master Pro (Probador de cable UTP)'),  
    (2, 1, 'Tester MicroScanner Pro'),  
    (3, 4, 'Kit tester Link Master (comprobador de cable VDV)'),  
    (4, 1, 'Tester Link Master (Comprobador de cable UTP)'),  
    (5,5, 'Herramienta de prensado'),  
    (6,5, 'Ponchadora cable UTP (WEL.spec)'),  
    (7,5, 'Ponchadora cable UTP (SNELL)'),  
    (8,5, 'Ponchadora cable UTP (QUEST)'),  
    (9,6, 'Ponchadora de Impacto'),  
    (10,4, 'Ponchadora cable UTP (PROSKIT)'),  
    (11,5, 'Prensa Terminal de cable coaxial'),  
    (12,2, 'Cortadora Cable Coaxial'),  
    (13,1, 'Kit Conectorización Fibra óptica VF-45'),  
    (14,1, 'Kit de conectorización de fibra óptica'),  
    (15,1, 'Kit de fibra óptica KAFO-01'),  
    (16,1, 'Kit de fibra óptica'),  
    (17,1, 'Optical Microscope'),  
    (18,1, 'Kit Fibra óptica \+ Power Meter \+ Cortadora Fibra Ftth \+ Vfl Fibra óptica monomodo KFIBRA'),  
    (19,4, 'Switch CATALYST 2960 Series 8-Puertos'),  
    (20,6, 'Switch 4200G-24 puertos'),  
    (21,6, 'Switch capa 3 CATALYST WS-C3650 24-Puertos'),  
    (22,1, 'Switch TRENDnet TEG-424WS'),  
    (23,1, 'Switch TRENDnet TEG-S24R'),  
    (24,4, 'Switch HUAWEI S1728GWR-4P 24 Puertos'),  
    (25,4, 'Router 1800 Series'),  
    (26,4, 'Router ISR4321'),  
    (27,4, 'Tarjeta de interface WIC NIM-2T (instaladas en los Router ISR4321)'),  
    (28,6, 'FIREWALL ASA 5508-X'),  
    (29,2, 'FIREWALL FORTIGATE 1240 B'),  
    (30,1, 'FORTIANALYZER 400 B'),  
    (31,1, 'Access Point DWL-2100AP'),  
    (32,1, 'Wireless AP Router TEW-411BRPplus'),  
    (33,3, 'Access Point DWL-2100AP WAP-1965'),  
    (34,4, 'Access Point SB WIRELESS N WAP-4410N'),  
    (35,6, 'Access Point N150 BRIDGE'),  
    (36,6, 'Tarjeta de red inalámbrica DWA-160 XtremeN'),  
    (37,11, 'Adaptador USB- Wifi b/g/N 150Mbps. De alta potencia TL-WN7200ND'),  
    (38,6, 'Tarjeta BeagleBone Black'),  
    (39,6, 'Osciloscopio Digital Rigol DS1102E-2Channel-100MHz-1GSa/s'),  
    (40,5, 'Generador Digital DG1022'),  
    (41,6, 'Fuente DC DUAL HY3005F-3'),  
    (42,4, 'Cámara IP DCS-5220'),  
    (43,4, 'Reflector con base para radio Canopy'),  
    (44,4, 'Plataforma de banda ancha inalámbrica Motorola 5400BHG'),  
    (45,4, 'Adaptador de corriente Radio Canopy'),  
    (46,4, 'Universal ethernet Surge Suppressor 600SSD'),  
    (47,3, 'Sensor Modelo HA2401GX-1000'),  
    (48,2, 'Elevador para antenas de 5mt'),  
    (49,10, 'Multímetro digital UT70A'),  
    (50,7, 'Punta Lógica DP-52 50MHz'),  
    (52,2, 'Antena Indoor Omnidirectional TEW-IA04O'),  
    (53,2, 'Antena Indoor Directional 20db TEW-IA06D'),  
    (54,2, 'Antena Wireless con accesorios ANT-0M8'),  
    (55,2, 'Antena Directional Outdoor 14 dBi TEW-OA14DK'),  
    (56,4, 'Antena Feed Power Beam AC PBE-5AC-500'),  
    (57,1, 'Antena de Grilla'),  
    (58,2, 'Antena Semiparabólica HG2424G'),  
    (59,1, 'Sistema radio frecuencia MGAR3-23N'),  
    (60,2, 'Antena Yagi Direccional'),  
    (61,2, 'Protector de grilla 3GHz (Lightning Arrester)');

**SCRIPT AREA ESTUDIO**

INSERT INTO public.area\_estudio (id\_area, area)  
VALUES   
    (1, 'Fundamentos de Ingeniería'),  
    (2, 'Electrónica Básica'),  
    (3, 'Desarrollo de aplicaciones WEB'),  
    (4, 'IoT'),  
    (5, 'Electiva Disciplinar: Domótica'),  
    (6, 'Sistemas Operativos'),  
    (7, 'Teoría General de Sistemas'),  
    (8, 'Redes de Datos'),  
    (9, 'Tesis de Grado'),  
    (10, 'Transmisión de datos'),  
    (11, 'Redes y Comunicaciones');

**SCRIPT HORARIOS INV REDES**

DELETE FROM horarios\_inv\_redes;  
DO $$  
DECLARE  
    fecha DATE;  
    dia INT;  
    hora INT;  
    id INT := 0;  \-- Inicializa el ID  
BEGIN  
    FOR dia IN 0..6 LOOP  \-- Ciclo para los dÃ­as de la semana (0 \= Lunes, 6 \= SÃ¡bado)  
        fecha := CURRENT\_DATE \- (EXTRACT(DOW FROM CURRENT\_DATE)::int) \+ dia \+ 1;  \-- Calcula la fecha del di­a actual  
        FOR hora IN 6..20 LOOP  \-- Ciclo para las horas (de 06:00 a 20:00)  
            INSERT INTO horarios\_inv\_redes (id, cantidad\_parcial, fecha, hora, name) VALUES   
                (id \+ 1, 0, fecha, (hora || ':00')::time, 'Kit tester Link Master Pro (Probador de cable UTP)'),  
                (id \+ 2, 0, fecha, (hora || ':00')::time, 'Tester MicroScanner Pro'),  
                (id \+ 3, 0, fecha, (hora || ':00')::time, 'Kit tester Link Master (comprobador de cable VDV)'),  
                (id \+ 4, 0, fecha, (hora || ':00')::time, 'Tester Link Master (Comprobador de cable UTP)'),  
                (id \+ 5, 0, fecha, (hora || ':00')::time, 'Herramienta de prensado'),  
                (id \+ 6, 0, fecha, (hora || ':00')::time, 'Ponchadora cable UTP (WEL.spec)'),  
                (id \+ 7, 0, fecha, (hora || ':00')::time, 'Ponchadora cable UTP (SNELL)'),  
                (id \+ 8, 0, fecha, (hora || ':00')::time, 'Ponchadora cable UTP (QUEST)'),  
                (id \+ 9, 0, fecha, (hora || ':00')::time, 'Ponchadora de Impacto'),  
                (id \+ 10, 0, fecha, (hora || ':00')::time, 'Ponchadora cable UTP (PROSKIT)'),  
                (id \+ 11, 0, fecha, (hora || ':00')::time, 'Prensa Terminal de cable coaxial'),  
                (id \+ 12, 0, fecha, (hora || ':00')::time, 'Cortadora Cable Coaxial'),  
                (id \+ 13, 0, fecha, (hora || ':00')::time, 'Kit Conectorización Fibra óptica VF-45'),  
                (id \+ 14, 0, fecha, (hora || ':00')::time, 'Kit de conectorización de fibra óptica'),  
                (id \+ 15, 0, fecha, (hora || ':00')::time, 'Kit de fibra óptica KAFO-01'),  
                (id \+ 16, 0, fecha, (hora || ':00')::time, 'Kit de fibra óptica'),  
                (id \+ 17, 0, fecha, (hora || ':00')::time, 'Optical Microscope'),  
                (id \+ 18, 0, fecha, (hora || ':00')::time, 'Kit Fibra óptica \+ Power Meter \+ Cortadora Fibra Ftth \+ Vfl Fibra óptica monomodo KFIBRA'),  
                (id \+ 19, 0, fecha, (hora || ':00')::time, 'Switch CATALYST 2960 Series 8-Puertos'),  
                (id \+ 20, 0, fecha, (hora || ':00')::time, 'Switch 4200G-24 puertos'),  
                (id \+ 21, 0, fecha, (hora || ':00')::time, 'Switch capa 3 CATALYST WS-C3650 24-Puertos'),  
                (id \+ 22, 0, fecha, (hora || ':00')::time, 'Switch TRENDnet TEG-424WS'),  
                (id \+ 23, 0, fecha, (hora || ':00')::time, 'Switch TRENDnet TEG-S24R'),  
                (id \+ 24, 0, fecha, (hora || ':00')::time, 'Switch HUAWEI S1728GWR-4P 24 Puertos'),  
                (id \+ 25, 0, fecha, (hora || ':00')::time, 'Router 1800 Series'),  
                (id \+ 26, 0, fecha, (hora || ':00')::time, 'Router ISR4321'),  
                (id \+ 27, 0, fecha, (hora || ':00')::time, 'Tarjeta de interface WIC NIM-2T (instaladas en los Router ISR4321)'),  
                (id \+ 28, 0, fecha, (hora || ':00')::time, 'FIREWALL ASA 5508-X'),  
                (id \+ 29, 0, fecha, (hora || ':00')::time, 'FIREWALL FORTIGATE 1240 B'),  
                (id \+ 30, 0, fecha, (hora || ':00')::time, 'FORTIANALYZER 400 B'),  
                (id \+ 31, 0, fecha, (hora || ':00')::time, 'Access Point DWL-2100AP'),  
                (id \+ 32, 0, fecha, (hora || ':00')::time, 'Wireless AP Router TEW-411BRPplus'),  
                (id \+ 33, 0, fecha, (hora || ':00')::time, 'Access Point DWL-2100AP WAP-1965'),  
                (id \+ 34, 0, fecha, (hora || ':00')::time, 'Access Point SB WIRELESS N WAP-4410N'),  
                (id \+ 35, 0, fecha, (hora || ':00')::time, 'Access Point N150 BRIDGE'),  
                (id \+ 36, 0, fecha, (hora || ':00')::time, 'Tarjeta de red inalámbrica DWA-160 XtremeN'),  
                (id \+ 37, 0, fecha, (hora || ':00')::time, 'Adaptador USB- Wifi b/g/N 150Mbps. De alta potencia TL-WN7200ND'),  
                (id \+ 38, 0, fecha, (hora || ':00')::time, 'Tarjeta BeagleBone Black'),  
                (id \+ 39, 0, fecha, (hora || ':00')::time, 'Osciloscopio Digital Rigol DS1102E-2Channel-100MHz-1GSa/s'),  
                (id \+ 40, 0, fecha, (hora || ':00')::time, 'Generador Digital DG1022'),  
                (id \+ 41, 0, fecha, (hora || ':00')::time, 'Fuente DC DUAL HY3005F-3'),  
                (id \+ 42, 0, fecha, (hora || ':00')::time, 'Cámara IP DCS-5220'),  
                (id \+ 43, 0, fecha, (hora || ':00')::time, 'Reflector con base para radio Canopy'),  
                (id \+ 44, 0, fecha, (hora || ':00')::time, 'Plataforma de banda ancha inalámbrica Motorola 5400BHG'),  
                (id \+ 45, 0, fecha, (hora || ':00')::time, 'Adaptador de corriente Radio Canopy'),  
                (id \+ 46, 0, fecha, (hora || ':00')::time, 'Universal ethernet Surge Suppressor 600SSD'),  
                (id \+ 47, 0, fecha, (hora || ':00')::time, 'Sensor Modelo HA2401GX-1000'),  
                (id \+ 48, 0, fecha, (hora || ':00')::time, 'Elevador para antenas de 5mt'),  
                (id \+ 49, 0, fecha, (hora || ':00')::time, 'Multímetro digital UT70A'),  
                (id \+ 50, 0, fecha, (hora || ':00')::time, 'Punta Lógica DP-52 50MHz'),  
                (id \+ 51, 0, fecha, (hora || ':00')::time, 'Antena Indoor Omnidirectional TEW-IA04O'),  
                (id \+ 52, 0, fecha, (hora || ':00')::time, 'Antena Indoor Directional 20db TEW-IA06D'),  
                (id \+ 53, 0, fecha, (hora || ':00')::time, 'Antena Wireless con accesorios ANT-0M8'),  
                (id \+ 54, 0, fecha, (hora || ':00')::time, 'Antena Directional Outdoor 14 dBi TEW-OA14DK'),  
                (id \+ 55, 0, fecha, (hora || ':00')::time, 'Antena Feed Power Beam AC PBE-5AC-500'),  
                (id \+ 56, 0, fecha, (hora || ':00')::time, 'Antena de Grilla'),  
                (id \+ 57, 0, fecha, (hora || ':00')::time, 'Antena Semiparabólica HG2424G'),  
                (id \+ 58, 0, fecha, (hora || ':00')::time, 'Sistema radio frecuencia MGAR3-23N'),  
                (id \+ 59, 0, fecha, (hora || ':00')::time, 'Antena Yagi Direccional'),  
                (id \+ 60, 0, fecha, (hora || ':00')::time, 'Protector de grilla 3GHz (Lightning Arrester)');  
            id := id \+ 60;    
        END LOOP;  
    END LOOP;  
END $$;

