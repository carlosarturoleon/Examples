const crypto = require('crypto');

// Generate a 256-bit key and a 96-bit IV
const key = crypto.randomBytes(32);
const iv = crypto.randomBytes(12);

// Your plaintext data
const plaintext = 'Your payload to encrypt';

// Create an AES-256-GCM cipher
const cipher = crypto.createCipheriv('aes-256-gcm', key, iv);

// Encrypt the data
let encrypted = cipher.update(plaintext, 'utf8', 'hex');
encrypted += cipher.final('hex');

// Get the authentication tag
const tag = cipher.getAuthTag();

// Concatenate IV, encrypted data, and authentication tag, and then encode in Base64
const output = Buffer.from(iv.toString('hex') + encrypted + tag.toString('hex'), 'hex').toString('base64');

// Output the results
console.log('Encrypted Data:', output);
console.log('Encryption Key:', key.toString('base64'));
