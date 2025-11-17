// 1. ฐานข้อมูลผู้ใช้จำลอง (ใช้ localStorage)
let usersDatabase = JSON.parse(localStorage.getItem('users')) || [
    { username: "member", password: "123", role: "Member" }, 
    { username: "admin", password: "admin", role: "Admin" }
];

function saveUsers() {
    localStorage.setItem('users', JSON.stringify(usersDatabase));
}
// บันทึกข้อมูลเริ่มต้นถ้ายังไม่มี
if (!localStorage.getItem('users')) {
    saveUsers(); 
}

// 2. รับองค์ประกอบ HTML
const form = document.getElementById('registrationForm');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('confirm-password');
const errorMsg = document.getElementById('errorMsg');

// 3. Logic เมื่อกดปุ่ม "ดำเนินการต่อ"
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
    
    if (usersDatabase.some(u => u.username === username)) {
        errorMsg.textContent = 'ชื่อผู้ใช้นี้ถูกใช้ไปแล้ว กรุณาใช้ชื่ออื่น';
        errorMsg.style.display = 'block';
        return;
    }

    // ลงทะเบียนสำเร็จ
    const newUser = { username: username, password: password, role: "Member" };
    usersDatabase.push(newUser);
    saveUsers(); 

    alert(`ลงทะเบียนผู้ใช้ "${username}" สำเร็จ!`);
    
    // ไปยังหน้า user-info.html เพื่อกรอก BMI
    // บันทึก currentUser ไว้เลย เพื่อให้รู้ว่ากำลังตั้งค่าของใคร
    localStorage.setItem('currentUser', newUser.username);
    localStorage.setItem('currentUserRole', newUser.role);
    
    window.location.href = 'user-info.html';
});