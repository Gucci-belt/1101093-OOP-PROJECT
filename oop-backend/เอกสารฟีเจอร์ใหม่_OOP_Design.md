# เอกสารฟีเจอร์ใหม่ - OOP Design Implementation

## 📋 สารบัญ
1. [Food Filtering & Sorting System](#1-food-filtering--sorting-system)
2. [BMI Progress Tracking System](#2-bmi-progress-tracking-system)
3. [OOP Design Patterns ที่ใช้](#3-oop-design-patterns-ที่ใช้)
4. [API Documentation](#4-api-documentation)
5. [ตัวอย่างการใช้งาน](#5-ตัวอย่างการใช้งาน)

---

## 1. Food Filtering & Sorting System

### 1.1 ภาพรวม
ระบบกรองและเรียงลำดับอาหารที่ยืดหยุ่น ใช้ **Strategy Pattern** และ **Composite Pattern** เพื่อให้สามารถเพิ่มเกณฑ์การกรองและเรียงลำดับใหม่ได้ง่าย

### 1.2 ความสามารถ
- ✅ **กรองอาหาร** ตามเกณฑ์ต่างๆ (แคลอรี่, ไขมัน, น้ำตาล, โซเดียม)
- ✅ **เรียงลำดับ** ตามค่าโภชนาการ (น้อย→มาก หรือ มาก→น้อย)
- ✅ **กรองหลายเกณฑ์พร้อมกัน** (Composite Filter)
- ✅ **เรียงหลายเกณฑ์พร้อมกัน** (Composite Sort)

### 1.3 โครงสร้างคลาส

#### 1.3.1 FoodFilter Interface
```java
public interface FoodFilter {
    List<Food> filter(List<Food> foods);
    boolean matches(Food food);
}
```

#### 1.3.2 Filter Classes

**LowCalorieFilter** - กรองอาหารแคลอรี่ต่ำ (≤ 400 kcal)
```java
LowCalorieFilter.standard()  // สร้าง Filter แบบมาตรฐาน
new LowCalorieFilter(400.0)  // สร้าง Filter แบบกำหนดเอง
```

**LowFatFilter** - กรองอาหารไขมันต่ำ (≤ 15g)
```java
LowFatFilter.standard()      // สร้าง Filter แบบมาตรฐาน
new LowFatFilter(15.0)       // สร้าง Filter แบบกำหนดเอง
```

**LowSugarFilter** - กรองอาหารน้ำตาลต่ำ (≤ 10g)
```java
LowSugarFilter.standard()
new LowSugarFilter(10.0)
```

**LowSodiumFilter** - กรองอาหารโซเดียมต่ำ (≤ 600mg)
```java
LowSodiumFilter.standard()
new LowSodiumFilter(600.0)
```

**CompositeFilter** - กรองหลายเกณฑ์พร้อมกัน
```java
CompositeFilter composite = new CompositeFilter(
    LowCalorieFilter.standard(),
    LowFatFilter.standard()
);
// จะกรองเฉพาะอาหารที่ผ่านทั้งสองเกณฑ์
```

#### 1.3.3 FoodSortStrategy Interface
```java
public interface FoodSortStrategy {
    List<Food> sort(List<Food> foods);
    int compare(Food a, Food b);
}
```

#### 1.3.4 Sort Strategy Classes

**CalorieSortStrategy** - เรียงตามแคลอรี่
```java
CalorieSortStrategy.ascending()   // น้อยไปมาก
CalorieSortStrategy.descending()  // มากไปน้อย
```

**FatSortStrategy** - เรียงตามไขมัน
```java
FatSortStrategy.ascending()
FatSortStrategy.descending()
```

**SugarSortStrategy** - เรียงตามน้ำตาล
```java
SugarSortStrategy.ascending()
SugarSortStrategy.descending()
```

**SodiumSortStrategy** - เรียงตามโซเดียม
```java
SodiumSortStrategy.ascending()
SodiumSortStrategy.descending()
```

**CompositeSortStrategy** - เรียงหลายเกณฑ์พร้อมกัน
```java
CompositeSortStrategy compositeSort = new CompositeSortStrategy(
    CalorieSortStrategy.ascending(),  // เรียงตามแคลอรี่ก่อน
    FatSortStrategy.ascending()       // ถ้าแคลอรี่เท่ากัน ให้เรียงตามไขมัน
);
```

### 1.4 FoodFilterService

Service ที่รวมการกรองและเรียงลำดับเข้าด้วยกัน

```java
@Service
public class FoodFilterService {
    // กรองและเรียงลำดับพร้อมกัน
    List<Food> filterAndSort(FoodFilter filter, FoodSortStrategy sortStrategy);
    
    // กรองอย่างเดียว
    List<Food> filter(FoodFilter filter);
    
    // เรียงลำดับอย่างเดียว
    List<Food> sort(FoodSortStrategy sortStrategy);
}
```

---

## 2. BMI Progress Tracking System

### 2.1 ภาพรวม
ระบบติดตามประวัติ BMI ของผู้ใช้ทุกครั้งที่คำนวณ BMI ช่วยให้ผู้ใช้สามารถดูพัฒนาการ BMI ของตัวเองได้ และสามารถใช้ข้อมูลนี้เพื่อสร้างกราฟแสดงการเปลี่ยนแปลงตามเวลา

### 2.2 ความสามารถ
- ✅ **บันทึก BMI อัตโนมัติ** ทุกครั้งที่คำนวณ
- ✅ **เก็บประวัติครบถ้วน** (น้ำหนัก, ส่วนสูง, BMI, หมวดหมู่, แคลอรี่ที่แนะนำ, วันที่/เวลา)
- ✅ **ดึงประวัติทั้งหมด** เรียงตามวันที่
- ✅ **ดึงประวัติตามช่วงวันที่** เหมาะสำหรับสร้างกราฟ
- ✅ **ดึง BMI ล่าสุด** ของผู้ใช้

### 2.3 โครงสร้างคลาส

#### 2.3.1 BMIRecord (Value Object)
คลาส Immutable สำหรับเก็บข้อมูล BMI แต่ละครั้ง

```java
public class BMIRecord {
    private final String username;
    private final double weight;           // น้ำหนัก (kg)
    private final double height;           // ส่วนสูง (cm)
    private final double bmi;              // ค่า BMI
    private final String category;         // หมวดหมู่ (เช่น "น้ำหนักปกติ")
    private final double recommendedCalories; // แคลอรี่ที่แนะนำ
    private final LocalDateTime timestamp; // เวลาที่บันทึก
    private final LocalDate date;          // วันที่ (สำหรับกรอง)
}
```

**คุณสมบัติ:**
- Immutable (ไม่สามารถแก้ไขได้หลังจากสร้าง)
- Thread-safe
- ใช้ Value Object Pattern

#### 2.3.2 BMIHistoryRepository

Repository สำหรับจัดการข้อมูล BMI History

```java
@Repository
public class BMIHistoryRepository {
    // บันทึก BMI Record ใหม่
    void save(BMIRecord record);
    
    // ดึงประวัติทั้งหมดของ user (เรียงตามวันที่)
    List<BMIRecord> findByUsername(String username);
    
    // ดึงประวัติตามช่วงวันที่
    List<BMIRecord> findByUsernameAndDateRange(
        String username, 
        LocalDate startDate, 
        LocalDate endDate
    );
    
    // ดึง BMI ล่าสุด
    BMIRecord findLatestByUsername(String username);
    
    // ลบประวัติตามช่วงวันที่
    int deleteByUsernameAndDateRange(
        String username, 
        LocalDate startDate, 
        LocalDate endDate
    );
}
```

#### 2.3.3 BMIHistoryService

Service สำหรับจัดการ Business Logic

```java
@Service
public class BMIHistoryService {
    // บันทึก BMI ใหม่
    BMIRecord recordBMI(String username, double weight, double height);
    
    // ดึงประวัติทั้งหมด
    List<BMIRecord> getBMIHistory(String username);
    
    // ดึงประวัติตามช่วงวันที่
    List<BMIRecord> getBMIHistoryByDateRange(
        String username, 
        LocalDate startDate, 
        LocalDate endDate
    );
    
    // ดึง BMI ล่าสุด
    BMIRecord getLatestBMI(String username);
    
    // ดึงข้อมูลสำหรับสร้างกราฟ
    List<BMIRecord> getBMIForGraph(
        String username, 
        LocalDate startDate, 
        LocalDate endDate
    );
}
```

#### 2.3.4 BMIHistoryResponse (DTO)

DTO สำหรับส่งข้อมูลไปยัง Frontend

```java
public class BMIHistoryResponse {
    private final String username;
    private final List<BMIDataPoint> dataPoints;
    
    // Inner class สำหรับเก็บข้อมูลแต่ละจุด
    public static class BMIDataPoint {
        private final String date;              // "2024-01-15"
        private final String timestamp;         // "2024-01-15 14:20:00"
        private final double bmi;               // 22.86
        private final double weight;            // 70
        private final double height;            // 175
        private final String category;          // "น้ำหนักปกติ"
        private final double recommendedCalories; // 2100
    }
}
```

---

## 3. OOP Design Patterns ที่ใช้

### 3.1 Strategy Pattern

**ใช้สำหรับ:** Food Filtering และ Sorting

**ประโยชน์:**
- แยกอัลกอริทึมการกรอง/เรียงลำดับออกมาเป็นคลาสแยกกัน
- เพิ่มกลยุทธ์ใหม่ได้ง่ายโดยไม่ต้องแก้ไขโค้ดเดิม
- สามารถเปลี่ยนกลยุทธ์ได้ตอน runtime

**ตัวอย่าง:**
```java
// เปลี่ยน Filter ได้ง่าย
FoodFilter filter1 = LowCalorieFilter.standard();
FoodFilter filter2 = LowFatFilter.standard();

// เปลี่ยน Sort Strategy ได้ง่าย
FoodSortStrategy sort1 = CalorieSortStrategy.ascending();
FoodSortStrategy sort2 = FatSortStrategy.descending();
```

### 3.2 Composite Pattern

**ใช้สำหรับ:** CompositeFilter และ CompositeSortStrategy

**ประโยชน์:**
- รวม Filter หลายตัวเข้าด้วยกัน
- รวม Sort Strategy หลายตัวเข้าด้วยกัน
- จัดการแบบ tree structure

**ตัวอย่าง:**
```java
// กรองหลายเกณฑ์พร้อมกัน
CompositeFilter filter = new CompositeFilter(
    LowCalorieFilter.standard(),
    LowFatFilter.standard(),
    LowSugarFilter.standard()
);

// เรียงหลายเกณฑ์พร้อมกัน
CompositeSortStrategy sort = new CompositeSortStrategy(
    CalorieSortStrategy.ascending(),
    FatSortStrategy.ascending()
);
```

### 3.3 Value Object Pattern

**ใช้สำหรับ:** BMIRecord

**ประโยชน์:**
- Immutable - ไม่สามารถแก้ไขได้หลังจากสร้าง
- Thread-safe
- แสดงถึงค่าที่มีเอกลักษณ์เฉพาะ

**ตัวอย่าง:**
```java
BMIRecord record = new BMIRecord(
    "member", 70, 175, 22.86, 
    "น้ำหนักปกติ", 2100, LocalDateTime.now()
);
// ไม่สามารถแก้ไขค่าใน record ได้ (ไม่มี setters)
```

### 3.4 Repository Pattern

**ใช้สำหรับ:** BMIHistoryRepository, FoodRepository

**ประโยชน์:**
- แยกการเข้าถึงข้อมูลออกจาก Business Logic
- เปลี่ยนแหล่งข้อมูลได้ง่าย (จาก memory เป็น database)
- ทำให้ทดสอบได้ง่ายขึ้น (Mock Repository)

---

## 4. API Documentation

### 4.1 Food Filtering & Sorting APIs

#### GET /api/foods
ดึงรายการอาหารทั้งหมด (พร้อม Filter และ Sort)

**Query Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `filterType` | String | No | `lowCalorie`, `lowFat`, `lowSugar`, `lowSodium` |
| `sortBy` | String | No | `calorie`, `fat`, `sugar`, `sodium` |
| `sortOrder` | String | No | `asc` (น้อยไปมาก), `desc` (มากไปน้อย) |

**ตัวอย่าง:**
```http
GET /api/foods?filterType=lowCalorie&sortBy=calorie&sortOrder=asc
GET /api/foods?filterType=lowFat&sortBy=fat&sortOrder=desc
GET /api/foods?sortBy=sugar&sortOrder=asc
```

**Response:**
```json
[
  {
    "name": "สลัดอกไก่",
    "kcal": 320,
    "fat": 10,
    "sugar": 3,
    "sodium": 400
  },
  {
    "name": "ต้มยำกุ้ง",
    "kcal": 350,
    "fat": 18,
    "sugar": 6,
    "sodium": 950
  }
]
```

---

### 4.2 BMI History APIs

#### POST /api/bmi/history/record
บันทึก BMI Record ใหม่

**Request Body:**
```json
{
  "username": "member",
  "weight": 70,
  "height": 175
}
```

**Response:**
```json
{
  "status": "success",
  "message": "บันทึก BMI สำเร็จ",
  "bmi": 22.86,
  "category": "น้ำหนักปกติ",
  "date": "2024-01-15"
}
```

---

#### GET /api/bmi/history/{username}
ดึงประวัติ BMI ทั้งหมดของ user

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `username` | String | Yes | Username ของผู้ใช้ |

**Response:**
```json
{
  "username": "member",
  "dataPoints": [
    {
      "date": "2024-01-01",
      "timestamp": "2024-01-01 10:30:00",
      "bmi": 23.5,
      "weight": 72,
      "height": 175,
      "category": "น้ำหนักปกติ",
      "recommendedCalories": 2160
    },
    {
      "date": "2024-01-15",
      "timestamp": "2024-01-15 14:20:00",
      "bmi": 22.86,
      "weight": 70,
      "height": 175,
      "category": "น้ำหนักปกติ",
      "recommendedCalories": 2100
    }
  ]
}
```

---

#### GET /api/bmi/history/{username}/range
ดึงประวัติ BMI ตามช่วงวันที่

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `username` | String | Yes | Username ของผู้ใช้ |

**Query Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `startDate` | String | Yes | วันที่เริ่มต้น (format: `YYYY-MM-DD`) |
| `endDate` | String | Yes | วันที่สิ้นสุด (format: `YYYY-MM-DD`) |

**ตัวอย่าง:**
```http
GET /api/bmi/history/member/range?startDate=2024-01-01&endDate=2024-01-31
```

**Response:** (เหมือน GET /api/bmi/history/{username})

---

#### GET /api/bmi/history/{username}/latest
ดึง BMI ล่าสุดของ user

**Path Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `username` | String | Yes | Username ของผู้ใช้ |

**Response:**
```json
{
  "status": "success",
  "data": {
    "date": "2024-01-15",
    "timestamp": "2024-01-15 14:20:00",
    "bmi": 22.86,
    "weight": 70,
    "height": 175,
    "category": "น้ำหนักปกติ",
    "recommendedCalories": 2100
  }
}
```

---

## 5. ตัวอย่างการใช้งาน

### 5.1 ตัวอย่าง Frontend Code (JavaScript)

#### 5.1.1 กรองอาหารแคลอรี่ต่ำ

```javascript
// กรองอาหารแคลอรี่ต่ำ และเรียงตามแคลอรี่ (น้อยไปมาก)
async function getLowCalorieFoods() {
    const response = await fetch(
        '/api/foods?filterType=lowCalorie&sortBy=calorie&sortOrder=asc'
    );
    const foods = await response.json();
    console.log('อาหารแคลอรี่ต่ำ:', foods);
}
```

#### 5.1.2 บันทึก BMI และดูประวัติ

```javascript
// บันทึก BMI ใหม่
async function recordBMI(username, weight, height) {
    const response = await fetch('/api/bmi/history/record', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username,
            weight: weight,
            height: height
        })
    });
    const result = await response.json();
    console.log('บันทึก BMI สำเร็จ:', result);
}

// ดึงประวัติ BMI ทั้งหมด
async function getBMIHistory(username) {
    const response = await fetch(`/api/bmi/history/${username}`);
    const history = await response.json();
    console.log('ประวัติ BMI:', history);
    
    // สร้างกราฟจากข้อมูล
    const dates = history.dataPoints.map(dp => dp.date);
    const bmiValues = history.dataPoints.map(dp => dp.bmi);
    plotBMIGraph(dates, bmiValues);
}

// ดึงประวัติ BMI ตามช่วงวันที่
async function getBMIHistoryByRange(username, startDate, endDate) {
    const response = await fetch(
        `/api/bmi/history/${username}/range?startDate=${startDate}&endDate=${endDate}`
    );
    const history = await response.json();
    return history;
}

// ดึง BMI ล่าสุด
async function getLatestBMI(username) {
    const response = await fetch(`/api/bmi/history/${username}/latest`);
    const result = await response.json();
    if (result.status === 'success') {
        console.log('BMI ล่าสุด:', result.data);
        return result.data;
    }
}
```

### 5.2 ตัวอย่าง Backend Code (Java)

#### 5.2.1 สร้าง Composite Filter

```java
// กรองอาหารแคลอรี่ต่ำ และไขมันต่ำ
CompositeFilter healthyFilter = new CompositeFilter(
    LowCalorieFilter.standard(),  // ≤ 400 kcal
    LowFatFilter.standard()       // ≤ 15g fat
);

List<Food> healthyFoods = foodFilterService.filter(healthyFilter);
```

#### 5.2.2 สร้าง Composite Sort Strategy

```java
// เรียงตามแคลอรี่ก่อน ถ้าเท่ากันให้เรียงตามไขมัน
CompositeSortStrategy sortStrategy = new CompositeSortStrategy(
    CalorieSortStrategy.ascending(),  // เรียงแคลอรี่น้อยไปมากก่อน
    FatSortStrategy.ascending()       // ถ้าแคลอรี่เท่ากัน เรียงไขมันน้อยไปมาก
);

List<Food> sortedFoods = foodFilterService.sort(sortStrategy);
```

#### 5.2.3 ใช้งาน BMIHistoryService

```java
@Autowired
private BMIHistoryService bmiHistoryService;

// บันทึก BMI ใหม่
BMIRecord record = bmiHistoryService.recordBMI("member", 70, 175);

// ดึงประวัติทั้งหมด
List<BMIRecord> history = bmiHistoryService.getBMIHistory("member");

// ดึงประวัติตามช่วงวันที่
LocalDate start = LocalDate.of(2024, 1, 1);
LocalDate end = LocalDate.of(2024, 1, 31);
List<BMIRecord> monthlyHistory = bmiHistoryService.getBMIHistoryByDateRange(
    "member", start, end
);

// ดึง BMI ล่าสุด
BMIRecord latest = bmiHistoryService.getLatestBMI("member");
```

### 5.3 ตัวอย่างการใช้กับ Chart Library

```javascript
// สร้างกราฟ BMI Progress ด้วย Chart.js
async function plotBMIGraph(username) {
    const history = await getBMIHistory(username);
    
    const dates = history.dataPoints.map(dp => dp.date);
    const bmiValues = history.dataPoints.map(dp => dp.bmi);
    const weights = history.dataPoints.map(dp => dp.weight);
    
    const ctx = document.getElementById('bmiChart').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: dates,
            datasets: [
                {
                    label: 'BMI',
                    data: bmiValues,
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                },
                {
                    label: 'Weight (kg)',
                    data: weights,
                    borderColor: 'rgb(255, 99, 132)',
                    yAxisID: 'y1'
                }
            ]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: false,
                    title: {
                        display: true,
                        text: 'BMI'
                    }
                },
                y1: {
                    position: 'right',
                    title: {
                        display: true,
                        text: 'Weight (kg)'
                    }
                }
            }
        }
    });
}
```

---

## 6. สรุป

### 6.1 Food Filtering & Sorting System
- ✅ ใช้ **Strategy Pattern** ทำให้เพิ่ม Filter/Sort ใหม่ได้ง่าย
- ✅ ใช้ **Composite Pattern** ทำให้รวมหลายเกณฑ์ได้
- ✅ รองรับการกรองและเรียงลำดับแบบยืดหยุ่น
- ✅ ใช้ผ่าน API endpoint เดิม (`/api/foods`) โดยเพิ่ม Query Parameters

### 6.2 BMI Progress Tracking System
- ✅ ใช้ **Value Object Pattern** สำหรับ BMIRecord (Immutable)
- ✅ ใช้ **Repository Pattern** แยกการเข้าถึงข้อมูล
- ✅ บันทึกประวัติอัตโนมัติทุกครั้งที่คำนวณ BMI
- ✅ รองรับการดึงข้อมูลตามช่วงวันที่สำหรับสร้างกราฟ

### 6.3 ประโยชน์ของการใช้ OOP Design Patterns
1. **ยืดหยุ่น (Flexibility)** - เพิ่มฟีเจอร์ใหม่ได้ง่าย
2. **บำรุงรักษาง่าย (Maintainability)** - โค้ดอ่านง่าย แก้ไขง่าย
3. **ทดสอบได้ง่าย (Testability)** - แยกส่วนชัดเจน Mock ได้ง่าย
4. **นำกลับมาใช้ได้ (Reusability)** - ใช้โค้ดเดิมได้ซ้ำๆ
5. **ขยายได้ (Extensibility)** - เพิ่มฟีเจอร์ใหม่โดยไม่ต้องแก้โค้ดเดิม

---

**สร้างเมื่อ:** 2024-01-15  
**เวอร์ชัน:** 1.0  
**ผู้พัฒนา:** OOP Project Team

