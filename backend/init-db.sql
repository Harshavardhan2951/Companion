GRANT ALL PRIVILEGES ON DATABASE aegis_core_db TO aegis_app;
GRANT ALL ON SCHEMA public TO aegis_app;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    role_id INT REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS emergency_contacts (
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES users(id) ON DELETE CASCADE,
    caregiver_name VARCHAR(100) NOT NULL,
    caregiver_phone VARCHAR(20) NOT NULL,
    priority_order INT DEFAULT 1,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS emergency_events (
    id BIGSERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    event_type VARCHAR(50) NOT NULL,
    escalation_status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    additional_info TEXT
);

CREATE INDEX IF NOT EXISTS idx_emergency_user_id ON emergency_events(user_id);
CREATE INDEX IF NOT EXISTS idx_emergency_created_at ON emergency_events(created_at DESC);

INSERT INTO roles (role_name) VALUES ('PATIENT'), ('CAREGIVER'), ('ADMIN')
ON CONFLICT (role_name) DO NOTHING;
