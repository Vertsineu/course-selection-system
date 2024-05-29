CREATE TABLE IF NOT EXISTS tbl_course (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    code            VARCHAR(32) UNIQUE NOT NULL,
    name_cn         VARCHAR(255),
    name_en         VARCHAR(255),
    category_cn     VARCHAR(255),
    category_en     VARCHAR(255),
    classify_cn     VARCHAR(255),
    classify_en     VARCHAR(255),
    gradation_cn    VARCHAR(32),
    gradation_en    VARCHAR(32),
    type_cn         VARCHAR(32),
    type_en         VARCHAR(32),
    credits          INT,
    education_cn    VARCHAR(32),
    education_en    VARCHAR(32),
    period_total    INT
) ENGINE= InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS tbl_class (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    code            VARCHAR(255) UNIQUE NOT NULL,
    course_id       INT,
    campus_cn       VARCHAR(255),
    campus_en       VARCHAR(255),
    type_cn         VARCHAR(255),
    type_en         VARCHAR(255),
    exam_mode_cn    VARCHAR(255),
    exam_mode_en    VARCHAR(255),
    graduate_and_post_graduate  BOOLEAN,
    department_id   INT,
    period_per_week INT,
    limit_count     INT,
    teach_lang_cn   VARCHAR(255),
    teach_lang_en   VARCHAR(255),
    FOREIGN KEY (course_id) REFERENCES tbl_course(id)
) ENGINE= InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS tbl_department(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    code            VARCHAR(255) UNIQUE NOT NULL,
    name_cn         VARCHAR(255),
    name_en         VARCHAR(255),
    college         BOOLEAN
) ENGINE= InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS tbl_teacher(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name_cn         VARCHAR(255),
    name_en         VARCHAR(255),
    department_id   INT,
    FOREIGN KEY (department_id) REFERENCES tbl_department(id)
) ENGINE= InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS tbl_student(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    number          VARCHAR(32) UNIQUE NOT NULL,
    password        VARCHAR(255) NOT NULL,
    department_id   INT
) ENGINE= InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS tbl_tpc (
    class_id        INT,
    time_week       VARCHAR(255),
    time_day        VARCHAR(255),
    time_period     VARCHAR(255),
    place           VARCHAR(255),
    teacher_id      INT,
    FOREIGN KEY (class_id) REFERENCES tbl_class(id),
    FOREIGN KEY (teacher_id) REFERENCES tbl_teacher(id)
) ENGINE= InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS tbl_teacher_class (
    teacher_id      INT,
    class_id        INT,
    FOREIGN KEY (class_id) REFERENCES tbl_class(id),
    FOREIGN KEY (teacher_id) REFERENCES tbl_teacher(id),
    PRIMARY KEY (teacher_id, class_id)
) ENGINE= InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS tbl_student_class (
    student_id      INT,
    class_id        INT,
    FOREIGN KEY (student_id) REFERENCES tbl_student(id),
    FOREIGN KEY (class_id) REFERENCES tbl_class(id),
    PRIMARY KEY (student_id, class_id)
) ENGINE= InnoDB DEFAULT CHARSET = utf8mb4;
