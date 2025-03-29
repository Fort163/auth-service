#!/bin/bash


psql -U postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'auth_sso'" | grep -q 1 || psql -U postgres -c "CREATE DATABASE auth_sso"

psql -U postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'auth_sso_test'" | grep -q 1 || psql -U postgres -c "CREATE DATABASE auth_sso_test"
