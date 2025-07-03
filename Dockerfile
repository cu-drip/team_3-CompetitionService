FROM postgres:16-alpine
COPY ddl.sql /docker-entrypoint-initdb.d/
