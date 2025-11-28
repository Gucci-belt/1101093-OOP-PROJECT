// 🎯 1. ลบฐานข้อมูลจำลอง (usersDatabase) ทิ้งไปเลย
//    เพราะ "สมอง" (ฐานข้อมูล) ย้ายไปอยู่ที่ Java Backend แล้ว

// 2. รับองค์ประกอบ HTML (เหมือนเดิม)
const form = document.getElementById('loginForm');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const errorMsg = document.getElementById('errorMsg');

// 3. Logic เมื่อกดปุ่ม "เข้าสู่ระบบ" (ปรับปรุงใหม่)
form.addEventListener('submit', function(e) {
    e.preventDefault(); // ป้องกันการ Submit ฟอร์มแบบดั้งเดิม

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    errorMsg.style.display = 'none'; 
    
    // การตรวจสอบข้อมูลเบื้องต้น (เหมือนเดิม)
    if (!username || !password) {
        errorMsg.textContent = 'กรุณากรอกชื่อผู้ใช้และรหัสผ่านให้ครบถ้วน';
        errorMsg.style.display = 'block';
        return; 
    }

    // 🎯 4. นี่คือส่วนที่เปลี่ยนไปทั้งหมด!
    //    เราจะ "ส่ง" ข้อมูลนี้ไปให้ Java Backend ที่รันอยู่ที่ port 8080
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
            // 7. Login สำเร็จ! (Java ยืนยันแล้ว)
            //    เรายังใช้ localStorage เพื่อ "จำ" ว่าใคร login อยู่
            //    (แต่ไม่ได้ใช้มันเป็นฐานข้อมูลอีกต่อไป)
            localStorage.setItem('currentUser', data.username);
            localStorage.setItem('currentUserRole', data.role);
            
            // 8. เปลี่ยนหน้าตาม "role" ที่ Java ส่งมา
            if (data.role === 'Admin') {
                window.location.href = 'profile.html';
            } else {
                window.location.href = 'home.html'; 
            }
        } else {
            // 9. Login ล้มเหลว (Java บอกว่ารหัสผิด หรือไม่พบผู้ใช้)
            errorMsg.textContent = 'ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง';
            errorMsg.style.display = 'block';
        }
    })
    .catch(error => {
        // 10. กรณีที่เชื่อมต่อ Java Backend ไม่ได้ (เช่น ลืมกด Run Backend)
        console.error('Error connecting to backend:', error);
        errorMsg.textContent = 'ไม่สามารถเชื่อมต่อเซิร์ฟเวอร์ได้ (Backend ปิดอยู่?)';
        errorMsg.style.display = 'block';
    });
});