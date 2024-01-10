//----------------------Registration----------------------//
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

//----------------------Product Details----------------------//
function openPopup(productId) {
    let url = '/index/one/' + productId;
    window.open(url, 'Product Details', 'width=600,height=400');
}
function closeWindow() {
    window.close();
}

//----------------------Logout window----------------------//
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
        <a class="btn btn-danger" href="/logout">Logout</a>
        <button class="btn btn-secondary" onclick="closeLogoutModal()">Cancel</button>
    `;
    overlay.appendChild(modalContent);
    document.body.appendChild(overlay);
}
function closeLogoutModal() {
    // Remove the modal overlay
    const overlay = document.querySelector('.modal-overlay');
    if (overlay) {
        overlay.remove();
    }
}

//----------------------Checkout window----------------------//
function openCheckoutModal(event) {
    event.preventDefault();

    const overlay = document.createElement('div');
    overlay.classList.add('modal-overlay-checkout');

    const modalContent = document.createElement('div');
    modalContent.classList.add('modal-content-checkout');
    modalContent.innerHTML = `
        <h5>Checkout Confirmation</h5>
        <p>Are you ready to check out your basket?</p>
        <a class="btn btn-secondary" href="/user/checkout">Yes</a>
        <button class="btn btn-success" onclick="closeModal()">Keep Shopping</button>
    `;
    overlay.appendChild(modalContent);
    document.body.appendChild(overlay);
}
function closeModal() {
    const overlay = document.querySelector('.modal-overlay-checkout');
    if (overlay) {
        overlay.remove();
    }
}

//----------------------Onmouseover/OnmouseOut-Topnavbar----------------------//
document.addEventListener("DOMContentLoaded", function () {
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

