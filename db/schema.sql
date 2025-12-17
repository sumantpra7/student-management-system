-- Users Table for Authentication
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL, 
    role TEXT NOT NULL CHECK(role IN ('ADMIN', 'VIEWER'))
);

-- Students Table (Personal Details)
CREATE TABLE IF NOT EXISTS students (
    student_id INTEGER PRIMARY KEY AUTOINCREMENT,
    full_name TEXT NOT NULL,
    dob TEXT NOT NULL,
    gender TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    email TEXT NOT NULL,
    address TEXT NOT NULL,
    college_id_card_no TEXT UNIQUE NOT NULL
);

-- Academic Details Table
CREATE TABLE IF NOT EXISTS academic_details (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id INTEGER NOT NULL,
    course TEXT NOT NULL,
    semester INTEGER NOT NULL,
    current_cgpa REAL NOT NULL,
    backlogs INTEGER NOT NULL,
    attendance_percentage REAL NOT NULL,
    academic_year TEXT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- Fee Details Table
CREATE TABLE IF NOT EXISTS fee_details (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id INTEGER NOT NULL,
    total_fee REAL NOT NULL,
    paid_amount REAL NOT NULL,
    pending_fee REAL NOT NULL,
    last_payment_date TEXT,
    fee_status TEXT CHECK(fee_status IN ('PAID', 'PENDING', 'OVERDUE')),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- Sports & Activities Table
CREATE TABLE IF NOT EXISTS sports_details (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id INTEGER NOT NULL,
    sports_name TEXT,
    level TEXT, -- College, State, National
    extra_curriculars TEXT,
    is_sports_participant TEXT CHECK(is_sports_participant IN ('YES', 'NO')),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- Skills & Knowledge Table
CREATE TABLE IF NOT EXISTS skills (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    student_id INTEGER NOT NULL,
    programming_level TEXT, -- Beginner, Intermediate, Advanced
    communication_rating INTEGER, -- 1-10
    leadership_rating INTEGER, -- 1-10
    technical_skills TEXT, -- Comma separated
    overall_skill_score REAL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- Seed Data: default admin (Password should be hashed in production, but plaintext for this simple cli requirement)
-- email: admin@sms.com, password: admin123
INSERT OR IGNORE INTO users (email, password, role) VALUES ('admin@sms.com', 'admin123', 'ADMIN');
