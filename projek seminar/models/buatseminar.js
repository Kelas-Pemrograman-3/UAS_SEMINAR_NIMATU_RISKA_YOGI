const mongoose = require('mongoose')
const Schema = mongoose.Schema

const buatseminarSchema = new Schema({
    namaseminar: {
        type: String
    },
    tanggalseminar: {
        type: String
    },
    hariseminar: {
        type: String
    },
    tempatseminar:{
        type: String
    },
    pemateriseminar:{
        type: String
    },
})

module.exports = mongoose.model('buatseminar', buatseminarSchema)