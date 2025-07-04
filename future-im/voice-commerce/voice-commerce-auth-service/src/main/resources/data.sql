-- Insert default customer role if it doesn't exist
INSERT INTO role (name, description, is_default, is_system)
SELECT 'ROLE_CUSTOMER', 'Default role for customers', true, true
WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_CUSTOMER');

-- Insert basic permissions for customer role
INSERT INTO role_permission (role_id, permission)
SELECT r.id, 'PRODUCT_READ'
FROM role r
WHERE r.name = 'ROLE_CUSTOMER'
AND NOT EXISTS (
    SELECT 1 FROM role_permission
    WHERE role_id = r.id AND permission = 'PRODUCT_READ'
);

INSERT INTO role_permission (role_id, permission)
SELECT r.id, 'CART_CREATE'
FROM role r
WHERE r.name = 'ROLE_CUSTOMER'
AND NOT EXISTS (
    SELECT 1 FROM role_permission
    WHERE role_id = r.id AND permission = 'CART_CREATE'
);

INSERT INTO role_permission (role_id, permission)
SELECT r.id, 'CART_READ'
FROM role r
WHERE r.name = 'ROLE_CUSTOMER'
AND NOT EXISTS (
    SELECT 1 FROM role_permission
    WHERE role_id = r.id AND permission = 'CART_READ'
);

INSERT INTO role_permission (role_id, permission)
SELECT r.id, 'CART_UPDATE'
FROM role r
WHERE r.name = 'ROLE_CUSTOMER'
AND NOT EXISTS (
    SELECT 1 FROM role_permission
    WHERE role_id = r.id AND permission = 'CART_UPDATE'
);

INSERT INTO role_permission (role_id, permission)
SELECT r.id, 'CART_DELETE'
FROM role r
WHERE r.name = 'ROLE_CUSTOMER'
AND NOT EXISTS (
    SELECT 1 FROM role_permission
    WHERE role_id = r.id AND permission = 'CART_DELETE'
);

INSERT INTO role_permission (role_id, permission)
SELECT r.id, 'ORDER_CREATE'
FROM role r
WHERE r.name = 'ROLE_CUSTOMER'
AND NOT EXISTS (
    SELECT 1 FROM role_permission
    WHERE role_id = r.id AND permission = 'ORDER_CREATE'
);

INSERT INTO role_permission (role_id, permission)
SELECT r.id, 'ORDER_READ'
FROM role r
WHERE r.name = 'ROLE_CUSTOMER'
AND NOT EXISTS (
    SELECT 1 FROM role_permission
    WHERE role_id = r.id AND permission = 'ORDER_READ'
);
