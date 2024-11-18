let currentIndex = 0;
const images = document.querySelectorAll('.carousel-image');
const indicators = document.querySelectorAll('.carousel-indicators div');

function currentImage(index) {
    images[currentIndex].classList.remove('active');
    indicators[currentIndex].classList.remove('active');
    
    currentIndex = index;
    
    images[currentIndex].classList.add('active');
    indicators[currentIndex].classList.add('active');
    
    const offset = -currentIndex * 100; // -100% para desplazar la imagen actual fuera de vista
    document.querySelector('.carousel-images').style.transform = `translateX(${offset}%)`;
}

// Función para mover al siguiente índice
function nextImage() {
    currentIndex = (currentIndex + 1) % images.length; // Vuelve al inicio al llegar al final
    currentImage(currentIndex);
}

// Cambia la imagen automáticamente cada 3 segundos (3000 ms)
setInterval(nextImage, 3000);
