# ใช้ nginx เป็น base image สำหรับ serve static files
FROM nginx:alpine

# คัดลอกไฟล์ frontend ทั้งหมดไปยัง nginx html directory
COPY . /usr/share/nginx/html

# คัดลอก nginx config เพื่อให้ frontend เรียก backend ได้
COPY nginx.conf /etc/nginx/conf.d/default.conf

# เปิด port 80
EXPOSE 80

# nginx จะรันอัตโนมัติ

