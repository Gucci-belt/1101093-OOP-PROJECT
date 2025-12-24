// รับองค์ประกอบ HTML
const form = document.getElementById('loginForm');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const errorMsg = document.getElementById('errorMsg');
const loginBtn = document.getElementById('loginBtn');
const passwordToggle = document.getElementById('passwordToggle');
const successAnimation = document.getElementById('successAnimation');

// Password Toggle
if (passwordToggle) {
    passwordToggle.addEventListener('click', function() {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        const icon = this.querySelector('i');
        icon.classList.toggle('fa-eye');
        icon.classList.toggle('fa-eye-slash');
    });
}

// Input Focus Effects
[usernameInput, passwordInput].forEach(input => {
    if (input) {
        input.addEventListener('focus', function() {
            this.parentElement.classList.add('focused');
        });
        input.addEventListener('blur', function() {
            if (!this.value) {
                this.parentElement.classList.remove('focused');
            }
        });
    }
});

// 3. Logic เมื่อกดปุ่ม "เข้าสู่ระบบ" (ปรับปรุงใหม่)
form.addEventListener('submit', function(e) {
    e.preventDefault(); // ป้องกันการ Submit ฟอร์มแบบดั้งเดิม

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    errorMsg.textContent = '';
    errorMsg.style.display = 'none'; 
    
    // การตรวจสอบข้อมูลเบื้องต้น
    if (!username || !password) {
        errorMsg.textContent = 'กรุณากรอกชื่อผู้ใช้และรหัสผ่านให้ครบถ้วน';
        errorMsg.style.display = 'block';
        loginBtn.classList.remove('loading');
        return; 
    }

    // Show loading state
    loginBtn.classList.add('loading');
    loginBtn.disabled = true;

    // Send request to backend
    fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        // "ห่อ" username และ password เป็น JSON
        body: JSON.stringify({ 
            username: username, 
            password: password 
        }) 
    })
    .then(response => response.json()) // 5. แปลงคำตอบ (JSON) ที่ Java ส่งกลับมา
    .then(data => {
        // 6. 'data' คือสิ่งที่ Java ตอบกลับมา
        //    (เช่น { status: "success", role: "Admin", username: "admin" })

        if (data.status === 'success') {
            // Show success animation
            if (successAnimation) {
                successAnimation.classList.add('show');
                setTimeout(() => {
                    successAnimation.classList.remove('show');
                }, 2000);
            }
            
            localStorage.setItem('currentUser', data.username);
            localStorage.setItem('currentUserRole', data.role);
            
            // Delay redirect to show success animation
            setTimeout(() => {
                if (data.role === 'Admin') {
                    window.location.href = 'profile.html';
                } else {
                    window.location.href = 'home.html'; 
                }
            }, 1500);
        } else {
            loginBtn.classList.remove('loading');
            loginBtn.disabled = false;
            errorMsg.textContent = 'ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง';
            errorMsg.style.display = 'block';
        }
    })
    .catch(error => {
        console.error('Error connecting to backend:', error);
        loginBtn.classList.remove('loading');
        loginBtn.disabled = false;
        errorMsg.textContent = 'ไม่สามารถเชื่อมต่อเซิร์ฟเวอร์ได้ (Backend ปิดอยู่?)';
        errorMsg.style.display = 'block';
    });
});