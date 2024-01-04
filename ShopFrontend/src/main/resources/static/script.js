

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

function openPopup(productId) {
    let url = '/index/one/' + productId;
    window.open(url, 'Product Details', 'width=600,height=400');
}


function closeWindow() {
    window.close();
}

function openLogoutModal() {
    // Create a modal overlay
    const overlay = document.createElement('div');
    overlay.classList.add('modal-overlay');

    // Create the modal content
    const modalContent = document.createElement('div');
    modalContent.classList.add('modal-content');
    modalContent.innerHTML = `
        <h5>Logout Confirmation</h5>
        <p>Are you sure you want to log out?</p>
        <a class="btn btn-primary" href="/logout">Logout</a>
        <button class="btn btn-secondary" onclick="closeLogoutModal()">Cancel</button>
    `;

    // Append modal content to the overlay
    overlay.appendChild(modalContent);

    // Append overlay to the body
    document.body.appendChild(overlay);
}

function closeLogoutModal() {
    // Remove the modal overlay
    const overlay = document.querySelector('.modal-overlay');
    if (overlay) {
        overlay.remove();
    }
}

document.addEventListener("DOMContentLoaded", function () {
    // Get all the text links in the top of the template
    const links = document.querySelectorAll('.navbar-nav a.nav-link');

    // Add event listeners for mouseover and mouseout
    links.forEach(function (link) {
        link.addEventListener('mouseover', function () {
            this.classList.add('fw-bold');
        });

        link.addEventListener('mouseout', function () {
            this.classList.remove('fw-bold');
        });
    });
});

