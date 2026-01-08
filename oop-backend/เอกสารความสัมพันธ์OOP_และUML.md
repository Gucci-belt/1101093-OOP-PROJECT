# 📊 เอกสารความสัมพันธ์ OOP และ UML Diagram

## 📋 สารบัญ
1. [ภาพรวมระบบ](#ภาพรวมระบบ)
2. [OOP Concepts ที่ใช้](#oop-concepts-ที่ใช้)
3. [Design Patterns](#design-patterns)
4. [ความสัมพันธ์ระหว่าง Class](#ความสัมพันธ์ระหว่าง-class)
5. [Layer Architecture](#layer-architecture)
6. [วิธีใช้ UML Diagram ใน draw.io](#วิธีใช้-uml-diagram-ใน-drawio)

---

## 🎯 ภาพรวมระบบ

ระบบ Healthy Food Delivery & BMI Calculator เป็นแอปพลิเคชันที่พัฒนาด้วย Java Spring Boot ใช้หลักการ OOP แบบครบถ้วน มีโครงสร้างดังนี้:

- **Models (Domain Entities)**: 8 classes
- **Interfaces**: 2 interfaces (สำหรับ Design Patterns)
- **Repositories (Data Access)**: 6 classes
- **Services (Business Logic)**: 7 classes
- **Controllers (Presentation)**: 6 classes
- **DTOs (Data Transfer)**: 9 classes
- **Filtering (Specification Pattern)**: 9 classes
- **Sorting (Strategy Pattern)**: 4 classes

---

## 🔷 OOP Concepts ที่ใช้

### 1. **Inheritance (การสืบทอด)**
```
User (Base Class)
├── Member (Child Class)
└── Admin (Child Class)
```

**ความหมาย:**
- `User` เป็น base class ที่มีข้อมูลพื้นฐาน (username, password, role)
- `Member` และ `Admin` สืบทอดจาก `User` และกำหนด role เป็น "Member" และ "Admin" ตามลำดับ
- ช่วยให้สามารถใช้ Polymorphism ได้ (เก็บใน List<User> ได้ทั้ง Member และ Admin)

**ตัวอย่างการใช้:**
```java
UserRepository stores List<User> but can contain:
- Member instances
- Admin instances
```

### 2. **Polymorphism (พหุรูป)**
- `UserRepository` เก็บ `List<User>` แต่สามารถเก็บทั้ง `Member` และ `Admin` ได้
- `AuthService.login()` คืนค่าเป็น `User` แต่จริงๆ อาจเป็น `Member` หรือ `Admin`
- `FoodSortStrategy` interface มีหลาย implementation (CalorieSortStrategy, FatSortStrategy, etc.)

### 3. **Encapsulation (การห่อหุ้ม)**
- ทุก class มี private fields และ public getters
- `User` class ไม่ให้อ่าน password โดยตรง ต้องใช้ `checkPassword()` method
- `BMIRecord` และ `FoodTracking` เป็น immutable (final fields, no setters)

### 4. **Abstraction (การทำให้เป็นนามธรรม)**
- `FoodSpecification` interface: ซ่อนรายละเอียดการกรองอาหาร
- `FoodSortStrategy` interface: ซ่อนรายละเอียดการเรียงลำดับ
- Controllers ใช้ Services โดยไม่รู้รายละเอียดการทำงาน

---

## 🎨 Design Patterns

### 1. **Specification Pattern** (Filtering System)

**โครงสร้าง:**
```
FoodSpecification (Interface)
├── LowCalorieSpecification
├── HighCalorieSpecification
├── LowSugarSpecification
├── HighSugarSpecification
├── LowFatSpecification
├── HighFatSpecification
├── LowSodiumSpecification
├── HighSodiumSpecification
└── RangeFilter (Generic filter)
```

**ความหมาย:**
- ใช้สำหรับสร้างเงื่อนไขการกรองอาหารที่ยืดหยุ่น
- สามารถรวมเงื่อนไขหลายๆ อันด้วย AND, OR, NOT
- แต่ละ specification มี method `isSatisfiedBy(Food food)` เพื่อตรวจสอบว่า food นั้นตรงเงื่อนไขหรือไม่

**ตัวอย่างการใช้งาน:**
```java
// กรองอาหารที่แคลอรี่ต่ำ และไขมันต่ำ
FoodSpecification filter = new LowCalorieSpecification()
    .and(new LowFatSpecification());

// ใช้กับ Stream API
List<Food> filtered = foods.stream()
    .filter(filter::isSatisfiedBy)
    .collect(Collectors.toList());
```

### 2. **Strategy Pattern** (Sorting System)

**โครงสร้าง:**
```
FoodSortStrategy (Interface)
├── CalorieSortStrategy
├── FatSortStrategy
├── SugarSortStrategy
└── SodiumSortStrategy
```

**ความหมาย:**
- ใช้สำหรับเปลี่ยนอัลกอริทึมการเรียงลำดับอาหารแบบ dynamic
- แต่ละ strategy มี method `sort(List<Food> foods)` และ `compare(Food a, Food b)`
- สามารถเพิ่ม strategy ใหม่ได้โดยไม่ต้องแก้ไข code เดิม

**ตัวอย่างการใช้งาน:**
```java
// เรียงตามแคลอรี่จากน้อยไปมาก
FoodSortStrategy strategy = CalorieSortStrategy.ascending();
List<Food> sorted = strategy.sort(foods);
```

### 3. **Factory Pattern** (Filter Creation)

**โครงสร้าง:**
```
FilterFactory
    + createFilter(filterType: String): FoodSpecification
```

**ความหมาย:**
- สร้าง `FoodSpecification` จาก string identifier
- ซ่อนรายละเอียดการสร้าง object ที่ซับซ้อน
- ทำให้ client code ไม่ต้องรู้ว่าต้องสร้าง class ไหน

**ตัวอย่างการใช้งาน:**
```java
// สร้าง filter จาก string
FoodSpecification filter = FilterFactory.createFilter("lowCalorie");
```

### 4. **Repository Pattern** (Data Access)

**โครงสร้าง:**
```
Repositories (6 classes)
├── UserRepository
├── FoodRepository
├── CartRepository
├── BMIHistoryRepository
├── FoodTrackingRepository
└── UserDataRepository
```

**ความหมาย:**
- แยก business logic ออกจาก data access logic
- ทำให้เปลี่ยนแหล่งข้อมูลได้ง่าย (เช่น จาก memory เป็น database)
- Services ใช้ Repositories เพื่อเข้าถึงข้อมูล โดยไม่รู้ว่าข้อมูลเก็บอยู่ที่ไหน

---

## 🔗 ความสัมพันธ์ระหว่าง Class

### 1. **Inheritance (การสืบทอด)**
```
User <|-- Member
User <|-- Admin
```
- **สัญลักษณ์**: ลูกศร solid หัวว่าง (`<|--`)
- **ความหมาย**: Member และ Admin สืบทอดจาก User

### 2. **Composition (การประกอบ - ความสัมพันธ์แน่น)**
```
CartItem *-- Food
```
- **สัญลักษณ์**: `*--` (solid diamond)
- **ความหมาย**: CartItem มี Food เป็นส่วนประกอบ หาก CartItem ถูกลบ Food reference ก็จะหายไปด้วย
- **Lifecycle**: Food ถูกควบคุมโดย CartItem

### 3. **Aggregation (การรวม - ความสัมพันธ์หลวม)**
```
UserRepository o-- User
FoodRepository o-- Food
CartRepository o-- CartItem
BMIHistoryRepository o-- BMIRecord
FoodTrackingRepository o-- FoodTracking
UserDataRepository o-- UserData
```
- **สัญลักษณ์**: `o--` (hollow diamond)
- **ความหมาย**: Repository เก็บ collection ของ objects แต่ objects สามารถมีอยู่ได้โดยอิสระ
- **Lifecycle**: Objects สามารถมีอยู่ได้โดยไม่ต้องมี Repository

### 4. **Dependency (การพึ่งพา)**
```
Controller --> Service
Service --> Repository
Service ..> Interface
Controller ..> DTO
```
- **สัญลักษณ์**: `-->` (solid arrow) หรือ `..>` (dashed arrow)
- **ความหมาย**: 
  - `-->`: ใช้ (uses) - dependency แข็ง (เช่น Service ต้องใช้ Repository)
  - `..>`: ใช้ (uses) - dependency อ่อน (เช่น Controller ใช้ DTO)

### 5. **Implementation (การนำ interface ไปใช้)**
```
FoodSpecification <|.. LowCalorieSpecification
FoodSortStrategy <|.. CalorieSortStrategy
```
- **สัญลักษณ์**: `<|..` (dashed arrow)
- **ความหมาย**: Class นำ interface ไป implement

---

## 🏗️ Layer Architecture

ระบบแบ่งเป็น 4 layers หลัก:

### 1. **Presentation Layer (Controllers)**
```
Controllers
├── AuthController
├── FoodController
├── CartController
├── BMIController
├── BMIHistoryController
└── FoodTrackingController
```
- **หน้าที่**: รับ request จาก client และส่ง response กลับ
- **ความสัมพันธ์**: ใช้ Services และ DTOs

### 2. **Business Logic Layer (Services)**
```
Services
├── AuthService
├── FoodService
├── FoodFilterService
├── CartService
├── BMIService
├── BMIHistoryService
└── FoodTrackingService
```
- **หน้าที่**: เก็บ business logic, validation, calculation
- **ความสัมพันธ์**: ใช้ Repositories, Interfaces (Specification, Strategy)

### 3. **Data Access Layer (Repositories)**
```
Repositories
├── UserRepository
├── FoodRepository
├── CartRepository
├── BMIHistoryRepository
├── FoodTrackingRepository
└── UserDataRepository
```
- **หน้าที่**: จัดการข้อมูล (CRUD operations)
- **ความสัมพันธ์**: เก็บ Models (aggregation)

### 4. **Domain Layer (Models)**
```
Models
├── User (abstract concept)
├── Member, Admin (concrete)
├── Food
├── CartItem
├── BMIRecord
├── FoodTracking
└── UserData
```
- **หน้าที่**: แทน entities ในระบบ
- **ความสัมพันธ์**: มี composition และ inheritance

---

## 🎨 สีใน UML Diagram

- **🔵 Controllers** (#E1F5FF) - สีฟ้าอ่อน
- **🟠 Services** (#FFF4E1) - สีส้มอ่อน
- **🟢 Repositories** (#E8F5E9) - สีเขียวอ่อน
- **🟣 Models** (#F3E5F5) - สีม่วงอ่อน
- **🟡 DTOs** (#FFE0B2) - สีส้ม
- **⚪ Interfaces** (#E0E0E0) - สีเทา
- **🔷 Factory** (#B2DFDB) - สีฟ้าอมเขียว

---

## 📥 วิธีใช้ UML Diagram ใน draw.io

### วิธีที่ 1: ใช้ PlantUML ใน draw.io

1. เปิด draw.io (https://app.diagrams.net/)
2. สร้าง diagram ใหม่
3. ไปที่ **More Shapes** → ค้นหา **PlantUML**
4. เพิ่ม PlantUML shape
5. Copy เนื้อหาจากไฟล์ `UML_Class_Diagram_Complete.puml`
6. Paste ลงใน PlantUML shape
7. draw.io จะแปลง PlantUML code เป็น diagram อัตโนมัติ

### วิธีที่ 2: Export จาก PlantUML แล้ว import

1. ไปที่ http://www.plantuml.com/plantuml/uml/
2. Copy เนื้อหาจากไฟล์ `UML_Class_Diagram_Complete.puml`
3. Paste ลงในเว็บไซต์
4. กด **Submit** เพื่อสร้าง diagram
5. Download เป็น PNG หรือ SVG
6. Import ไฟล์นั้นเข้า draw.io

### วิธีที่ 3: วาดใหม่ใน draw.io ตาม UML

1. เปิด draw.io
2. สร้าง diagram ใหม่ (เลือก **UML Class** template)
3. วาด class ตามที่ระบุในเอกสารนี้
4. ใช้สีตามที่กำหนด
5. เชื่อมความสัมพันธ์ด้วยเส้นที่ถูกต้อง:
   - Inheritance: ลูกศร solid หัวว่าง
   - Composition: Solid diamond
   - Aggregation: Hollow diamond
   - Dependency: Dashed arrow
   - Implementation: Dashed arrow หัวว่าง

---

## 📊 สรุปจำนวน Classes และ Interfaces

### Models (8 classes)
1. User (abstract base)
2. Member
3. Admin
4. Food
5. CartItem
6. BMIRecord
7. FoodTracking
8. UserData

### Interfaces (3 interfaces)
1. FoodSpecification
2. FoodSortStrategy
3. RangeExtractor (nested in RangeFilter)

### Filtering (9 classes + 1 factory)
1. FilterFactory
2. LowCalorieSpecification
3. HighCalorieSpecification
4. LowSugarSpecification
5. HighSugarSpecification
6. LowFatSpecification
7. HighFatSpecification
8. LowSodiumSpecification
9. HighSodiumSpecification
10. RangeFilter

### Sorting (4 classes)
1. CalorieSortStrategy
2. FatSortStrategy
3. SugarSortStrategy
4. SodiumSortStrategy

### Repositories (6 classes)
1. UserRepository
2. FoodRepository
3. CartRepository
4. BMIHistoryRepository
5. FoodTrackingRepository
6. UserDataRepository

### Services (7 classes)
1. AuthService
2. FoodService
3. FoodFilterService
4. CartService
5. BMIService
6. BMIHistoryService
7. FoodTrackingService

### Controllers (6 classes)
1. AuthController
2. FoodController
3. CartController
4. BMIController
5. BMIHistoryController
6. FoodTrackingController

### DTOs (9 classes)
1. LoginRequest
2. RegisterRequest
3. BMIRequest
4. BMIResponse
5. BMIHistoryResponse (with nested BMIDataPoint)
6. CartRequest
7. CartResponse (with nested CartItemData)
8. FoodTrackingRequest
9. FoodTrackingResponse

### Application (1 class)
1. OopBackendApplication

**รวมทั้งหมด**: ประมาณ **60+ classes/interfaces**

---

## 🔍 ตัวอย่างการไหลของข้อมูล (Data Flow)

### ตัวอย่าง: การกรองและเรียงลำดับอาหาร

```
1. Frontend → FoodController.getAllFoods()
   ↓
2. FoodController → FilterFactory.createFilter()
   ↓
3. FilterFactory → LowCalorieSpecification (implements FoodSpecification)
   ↓
4. FoodController → FoodFilterService.filterAndSort()
   ↓
5. FoodFilterService → FoodRepository.findAll()
   ↓
6. FoodRepository → Returns List<Food>
   ↓
7. FoodFilterService → Applies FoodSpecification filter
   ↓
8. FoodFilterService → Applies FoodSortStrategy sort
   ↓
9. FoodController → Returns filtered & sorted List<Food>
   ↓
10. Frontend ← Receives JSON response
```

---

## ✅ Checklist สำหรับการทำ UML

- [x] Inheritance relationships
- [x] Composition relationships
- [x] Aggregation relationships
- [x] Dependency relationships
- [x] Implementation relationships
- [x] All attributes (fields)
- [x] All methods
- [x] Access modifiers (public, private)
- [x] Design patterns annotations
- [x] Color coding by layer
- [x] Notes explaining relationships

---

## 📝 หมายเหตุ

1. **UML Diagram นี้เป็นมาตรฐาน** - ใช้สัญลักษณ์ตาม UML 2.5 specification
2. **Compatible with draw.io** - สามารถนำไปใช้ใน draw.io ได้ทันที
3. **ครบถ้วน** - ครอบคลุมทุก class และความสัมพันธ์ในโปรเจค
4. **พร้อมใช้งาน** - พร้อมสำหรับการนำเสนอและเอกสาร

---

**สร้างเมื่อ**: 2024
**เวอร์ชัน**: 1.0
**ผู้สร้าง**: AI Assistant

