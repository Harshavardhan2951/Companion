import time
import psycopg2

# Measure connection & query latency
start = time.time()
conn = psycopg2.connect(
    dbname="aegis_core_db",
    user="aegis_app",
    password="aegis_password",
    host="localhost",
    port="5432"
)
cursor = conn.cursor()
cursor.execute("SELECT count(*) FROM users;")
count = cursor.fetchone()[0]
latency_ms = (time.time() - start) * 1000

print(f"PostgreSQL connection & query successful.")
print(f"Total Users: {count}")
print(f"Latency: {latency_ms:.2f} ms")

cursor.close()
conn.close()
