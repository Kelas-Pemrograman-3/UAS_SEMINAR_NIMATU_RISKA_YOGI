const mongoose = require('mongoose')
const Schema = mongoose.Schema

const loginSchema = new Schema({
    email: {
        type: String,
    },
    password: {
        type: String
    },
    nama: {
        type: String
    },
    alamat: {
        type: String
    },
})
module.exports = mongoose.model('login', loginSchema)