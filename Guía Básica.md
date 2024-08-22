---

# Guía Básica de GitHub para el Proyecto de Chatbot y Página Web.

## Menú

1. [¿Qué es GitHub?](#1-qué-es-github)
2. [Cómo Empezar](#2-cómo-empezar)
   - [Instalación de Git en Windows](#21-instalación-de-git-en-windows)
   - [Instalación de Git en Linux](#22-instalación-de-git-en-linux)
   - [Configuración Inicial](#23-configuración-inicial)
3. [Obtener una Copia del Proyecto (Clonar)](#3-obtener-una-copia-del-proyecto-clonar)
4. [Trabajar en el Proyecto](#4-trabajar-en-el-proyecto)
   - [Crear una Rama](#41-crear-una-rama)
   - [Cambiar de Rama](#42-cambiar-de-rama)
   - [Guardar y Subir Cambios](#43-guardar-y-subir-cambios)
5. [Manejo de Archivos](#5-manejo-de-archivos)
   - [Crear un Archivo](#51-crear-un-archivo)
   - [Eliminar un Archivo](#52-eliminar-un-archivo)
6. [Documentos `.md`](#6-documentos-md)
7. [Licencia del Proyecto](#7-licencia-del-proyecto)

---

## 1. ¿Qué es GitHub?

**GitHub** es una plataforma en línea que permite a los desarrolladores trabajar juntos en proyectos de programación. Es como una red social para programadores, donde se pueden almacenar, compartir y colaborar en código.

GitHub se basa en **Git**, un sistema de control de versiones que lleva un registro de todos los cambios realizados en el código. 
Esto significa que puedes ver quién hizo qué cambio, revertir errores a versiones anteriores, y trabajar en diferentes partes de un proyecto al mismo tiempo sin problemas.

## Con GitHub, puedes:
- **Guardar el código** en repositorios (como carpetas en la nube).
- **Colaborar** con otros desarrolladores en proyectos compartidos.
- **Realizar un seguimiento** de los cambios en el código para asegurarte de que todo funcione bien.
- **Contribuir** a otros proyectos abiertos que te interesen.

Es especialmente útil para proyectos en equipo y es ampliamente utilizado en la industria del software.

## 2. Cómo Empezar

Para comenzar a usar GitHub, primero necesitamos instalar una herramienta llamada **Git** en tu computadora.

### 2.1 Instalación de Git en Windows

1. Ve a [git-scm.com](https://git-scm.com/downloads) y descarga el instalador.
2. Haz doble clic en el archivo que descargaste para instalar Git.
3. Sigue las instrucciones en pantalla (puedes dejar todo como está y solo hacer clic en "Siguiente" hasta el final).

### 2.2 Instalación de Git en Linux

1. Abre la aplicación **Terminal**.
2. Escribe este comando y presiona Enter:
   ```bash
   sudo apt-get install git
   ```
3. Escribe tu contraseña si te la pide y espera a que termine de instalar.

### 2.3 Configuración Inicial

Antes de empezar a usar Git, necesitamos decirle quién eres.

1. Abre la Terminal (si estás en Windows, busca "Git Bash").
2. Escribe estos comandos uno por uno:
   ```bash
   git config --global user.name "Tu Nombre"
   git config --global user.email "tuemail@example.com"
   ```
   Reemplaza `"Tu Nombre"` y `"tuemail@example.com"` con tu nombre y correo.

## 3. Obtener una Copia del Proyecto (Clonar)

Para trabajar en el proyecto, primero necesitamos hacer una copia en nuestra computadora.

1. Ve a la página del proyecto en GitHub.
2. Busca un botón que dice "Code" y haz clic en él.
3. Copia el enlace que aparece (debe empezar con `https://`).
4. Abre la Terminal (Git Bash en Windows) y escribe:
   ```bash
   git clone [pega-el-enlace-aquí]
   ```
5. Presiona Enter, y el proyecto se descargará en tu computadora.

## 4. Trabajar en el Proyecto

### 4.1 Crear una Rama

Para trabajar en una parte del proyecto sin afectar el trabajo de los demás, creamos una **rama**.

1. En la Terminal, escribe:
   ```bash
   git checkout -b mi-rama
   ```
   Reemplaza `mi-rama` con un nombre que describa lo que estás haciendo (por ejemplo, `arreglar-bug`).

### 4.2 Cambiar de Rama

Para moverte entre las diferentes ramas del proyecto:

1. Escribe en la Terminal:
   ```bash
   git checkout nombre-de-la-rama
   ```
   Reemplaza `nombre-de-la-rama` con la rama a la que quieras cambiar.

### 4.3 Guardar y Subir Cambios

Cuando termines de trabajar, necesitas **guardar** (commit) y **subir** (push) tus cambios.

1. Guarda tus cambios escribiendo:
   ```bash
   git add .
   git commit -m "Descripción de lo que hiciste"
   ```
2. Sube tus cambios a GitHub con:
   ```bash
   git push origin mi-rama
   ```
3. Tambien puedes hacer commits mas extensos, para eso puedes escribir:
   ```bash
   git commit
   ```
## 5. Manejo de Archivos

### 5.1 Crear un Archivo

1. Si estás en la Terminal, escribe:
   ```bash
   touch nombre-del-archivo.txt
   ```
2. O usa cualquier editor de texto para crear un archivo y guárdalo en la carpeta del proyecto.

### 5.2 Eliminar un Archivo

1. En la Terminal, escribe:
   ```bash
   rm nombre-del-archivo.txt
   ```
2. O selecciona el archivo y presiona "Eliminar" en tu computadora.

## 6. Documentos `.md`

### 6.1 ¿Qué es un Archivo `.md`?

Los archivos `.md` son simples documentos de texto que usamos para escribir notas, instrucciones, etc. Usan una sintaxis especial llamada Markdown que facilita el formateo del texto.

### 6.2 ¿Cómo Crear un Archivo `.md`?

1. Crea un archivo con la extensión `.md` como `instrucciones.md`.
2. Escribe en él usando esta guía:
   - **Encabezados**: Usa `#` para hacer títulos grandes. Ejemplo: `# Título Grande`
   - **Listas**: Usa `-` para hacer listas. Ejemplo:
     ```
     - Primer ítem
     - Segundo ítem
     ```
   - **Negrita**: Usa `**` para texto en negrita. Ejemplo: `**Este texto está en negrita**`

## 7. Licencia del Proyecto

Este proyecto está bajo una licencia llamada **MIT**, lo que significa que otras personas pueden usar nuestro código si respetan algunas condiciones.

### 7.1 ¿Cómo Incluir la Licencia?

1. Abre el archivo `README.md` del proyecto.
2. Añade lo siguiente al final:
   ```markdown
   ## Licencia

   Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
   ```

---
