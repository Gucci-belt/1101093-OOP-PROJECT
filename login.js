// 1. ฐานข้อมูลผู้ใช้จำลอง (โหลดจาก Local Storage)
const usersDatabase = JSON.parse(localStorage.getItem('users')) || [
    { username: "member", password: "123", role: "Member" }, 
    { username: "admin", password: "admin", role: "Admin" }
];

// 2. รับองค์ประกอบ HTML
const form = document.getElementById('loginForm');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const errorMsg = document.getElementById('errorMsg');


// 3. Logic เมื่อกดปุ่ม "เข้าสู่ระบบ"
form.addEventListener('submit', function(e) {
    e.preventDefault(); // ป้องกันการ Submit ฟอร์มแบบดั้งเดิม

    const username = usernameInput.value.trim();
    const password = passwordInput.value.trim();

    errorMsg.style.display = 'none'; 
    
    if (!username || !password) {
        errorMsg.textContent = 'กรุณากรอกชื่อผู้ใช้และรหัสผ่านให้ครบถ้วน';
        errorMsg.style.display = 'block';
        return; 
    }

    // ค้นหาผู้ใช้
    const foundUser = usersDatabase.find(u => 
        u.username === username && u.password === password
    );

    if (foundUser) {
        // Login สำเร็จ
        localStorage.setItem('currentUser', foundUser.username);
        localStorage.setItem('currentUserRole', foundUser.role);
        
        if (foundUser.role === 'Admin') {
            window.location.href = 'admin_add_data.html'; // (สมมติว่ามีหน้านี้)
        } else {
            // Member: ไปหน้าหลัก
            window.location.href = 'home.html'; 
        }
    } else {
        // Login ล้มเหลว
        errorMsg.textContent = 'ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง';
        errorMsg.style.display = 'block';
    }
});