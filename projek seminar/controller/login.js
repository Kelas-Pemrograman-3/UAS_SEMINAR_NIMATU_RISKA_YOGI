const loginmodel = require('../models/login')
const bcrypt = require('bcryptjs')


exports.insert = (data) => 
    new Promise((resolve, reject) => {
        bcrypt.hash(data.password, 10, (err, hash) => {
            data.password = hash
            loginmodel.find({
                email: data.email
               }).then(hasil => {
                if (hasil.length > 0) {
                   reject({
                       error: true,
                       pesan: 'email sudah digunakan'
                   })
                } else {
                    loginmodel.create(data)
                    .then(res => {
                        resolve({
                           error: false,
                           pesan: 'berhasil input data' 
                        })
                    }).catch(() => {
                        reject({
                            error: true,
                            pesan: 'email sudah digunakan'
                        })   
                    })
                }
               })
        })
    })

exports.login = (data) => 
    new Promise(async (resolve, reject) => {
        await loginmodel.findOne({
            email: data.email
        }).then(res => {
            console.log(res)
            if (res === null) {
                reject({
                    error: true,
                    pesan: 'email tidak terdaftar'
                })
            } else {
                let hashpassword = res.password
               if ( bcrypt.compareSync(data.password, hashpassword)) {
                   resolve({
                       error: false,
                       pesan: 'berhasil login',
                       data: res
                   })
               } else {
                   reject({
                       error: true,
                       pesan: 'password anda salah'
                   })
               }
            }
        })
    })


exports.getAll = () =>
    new Promise((resolve, reject) => {
        loginmodel.find()
            .then(res => {
                resolve({
                    error: false,
                    pesan: 'berhasil mengambil data',
                    data: res
                })
            }).catch(() => {
                reject({
                    error: true,
                    pesan: 'gagal mengambil data'
                })
            })
    })