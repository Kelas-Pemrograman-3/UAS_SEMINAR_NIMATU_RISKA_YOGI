const mongoose = require('mongoose')
const Schema = mongoose.Schema

const pesertaseminarSchema = new Schema({
    nama: {
        type: String
    },
    alamat: {
        type: String
    },
    email: {
        type: String
    }
})

module.exports = mongoose.model('pesertaseminar', pesertaseminarSchema)