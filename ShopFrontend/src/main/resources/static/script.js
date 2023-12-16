function openPopup(productId) {
    let url = '/index/one/' + productId;
    window.open(url, 'Product Details', 'width=600,height=400');
}

function validatePassword() {
    let password = document.getElementsByName('password')[0].value;
    let passwordError = document.getElementById('passwordError');

    let passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;

    if (!passwordRegex.test(password)) {
        passwordError.innerText = 'Password must contain at least one digit, one lowercase and one uppercase letter, and be at least 8 characters long.';
        return false;
    } else {
        passwordError.innerText = '';
        return true;
    }
}


// --------- Functions below, for fantastic text + random gif appearing ---------

// Array of GIFs (from static folder)
const gifPaths = [
    "/GifBunny.gif",
    "/GifMario.gif",
    "/GifMcZombie.gif",
    "/GifSonic.gif",

];

// Function to set initial position for the GIF and text at the bottom of the page
function setInitialPosition() {
    const gif = document.getElementById("randomGif");
    const text = document.getElementById("movingText");
    const screenWidth = window.innerWidth;
    const screenHeight = window.innerHeight;

    gif.style.top = screenHeight - 100 + "px"; // Adjust the height as needed
    gif.style.left = -100 + "px";

    text.style.top = screenHeight - 100 + "px"; // Adjust the height as needed
    text.style.left = -200 + "px"; // Adjust the initial position of the text

    // Randomly select a GIF path
    const randomGifPath = gifPaths[Math.floor(Math.random() * gifPaths.length)];
    gif.src = randomGifPath;
}

// Function to animate the GIF and text
function moveGifAndText() {
    const gif = document.getElementById("randomGif");
    const text = document.getElementById("movingText");
    const screenWidth = window.innerWidth;

    // Move the GIF and text to the right
    gif.style.left = parseInt(gif.style.left) + 1 + "px"; // Adjust the speed as needed
    text.style.left = parseInt(text.style.left) + 1 + "px"; // Adjust the speed as needed

    // Check if the GIF is out of bounds
    if (parseInt(gif.style.left) > screenWidth) {
        setInitialPosition(); // Set new random GIF and initial position when out of bounds
    }

    // Repeat the animation
    requestAnimationFrame(moveGifAndText);
}

// Set initial position and start the animation
setInitialPosition();
moveGifAndText();