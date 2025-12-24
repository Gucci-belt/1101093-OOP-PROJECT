// 2. รับองค์ประกอบ HTML
const form = document.getElementById('registrationForm');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('confirm-password');
const errorMsg = document.getElementById('errorMsg');
const passwordToggle = document.getElementById('passwordToggle');
const confirmPasswordToggle = document.getElementById('confirmPasswordToggle');

// Password Toggle สำหรับ password field
if (passwordToggle) {
    passwordToggle.addEventListener('click', function() {
        const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordInput.setAttribute('type', type);
        
        // เปลี่ยนไอคอน
        const icon = this.querySelector('i');
        if (type === 'password') {
            icon.classList.remove('fa-eye-slash');
            icon.classList.add('fa-eye');
        } else {
            icon.classList.remove('fa-eye');
            icon.classList.add('fa-eye-slash');
        }
    });
}

// Password Toggle สำหรับ confirm-password field
if (confirmPasswordToggle) {
    confirmPasswordToggle.addEventListener('click', function() {
        const type = confirmPasswordInput.getAttribute('type') === 'password' ? 'text' : 'password';
        confirmPasswordInput.setAttribute('type', type);
        
        // เปลี่ยนไอคอน
        const icon = this.querySelector('i');
        if (type === 'password') {
            icon.classList.remove('fa-eye-slash');
            icon.classList.add('fa-eye');
        } else {
            icon.classList.remove('fa-eye');
            icon.classList.add('fa-eye-slash');
        }
    });
}

// 3. Logic เมื่อกดปุ่ม "ดำเนินการต่อ" (ปรับปรุงใหม่)
form.addEventListener('submit', function(e) {
    e.preventDefault(); // หยุดการส่งฟอร์มแบบดั้งเดิม

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();
    const confirmPassword = confirmPasswordInput.value.trim();

    errorMsg.style.display = 'none'; 
    
    // 🎯 การตรวจสอบข้อมูลพื้นฐาน
    if (!username || !password || !confirmPassword) {
        errorMsg.textContent = 'กรุณากรอกข้อมูลให้ครบถ้วน';
        errorMsg.style.display = 'block';
        return; 
    }
    
    if (password !== confirmPassword) {
        errorMsg.textContent = 'รหัสผ่านที่ป้อนไม่ตรงกัน';
        errorMsg.style.display = 'block';
        return;
    }

    // 🎯 4. นี่คือส่วนที่เปลี่ยนไปทั้งหมด!
    //    เราจะ "ส่ง" ข้อมูลนี้ไปให้ Java Backend ที่รันอยู่ที่ port 8080
    fetch('http://localhost:8080/register', {
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
        //    (เช่น { status: "success" } หรือ { status: "failed", message: "..." })

        if (data.status === 'success') {
            // 7. ลงทะเบียนสำเร็จ! (Java ยืนยันแล้ว)
            alert(`ลงทะเบียนผู้ใช้ "${username}" สำเร็จ!`);
            
            // 8. เด้งไปหน้า login เพื่อให้ผู้ใช้ login เข้าสู่ระบบ
            window.location.href = 'login.html';
        } else {
            // 10. ลงทะเบียนล้มเหลว (Java บอกว่าชื่อซ้ำ)
            errorMsg.textContent = data.message || 'ชื่อผู้ใช้นี้ถูกใช้ไปแล้ว กรุณาใช้ชื่ออื่น';
            errorMsg.style.display = 'block';
        }
    })
    .catch(error => {
        // 11. กรณีที่เชื่อมต่อ Java Backend ไม่ได้ (เช่น ลืมกด Run Backend)
        console.error('Error connecting to backend:', error);
        errorMsg.textContent = 'ไม่สามารถเชื่อมต่อเซิร์ฟเวอร์ได้ (Backend ปิดอยู่?)';
        errorMsg.style.display = 'block';
    });
});