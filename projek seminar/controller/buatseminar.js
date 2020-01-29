const buatseminarModel = require('../models/buatseminar')
const ObjectId = require ('mongoose').Types.ObjectId

exports.inputseminar = (data) =>
  new Promise((resolve, reject) => {
    buatseminarModel.create(data)
      .then(res => {
        resolve({
          error: false,
          pesan: 'Berhasil Input Data'
        })
      })
      .catch(() => {
        reject({
          error: true,
          pesan: 'Gagal Input Data'
        })
      })
  })

  exports.getAllseminar = () =>
  new Promise((resolve, reject) => {
    buatseminarModel.find()
      .then(res => {
        resolve({
          error: false,
          pesan: 'Berhasil Mengambil Data',
          data: res
        })
      })
      .catch(() => {
        reject({
          error: true,
          pesan: 'Gagal Mengambil Data'
        })
      })
    })

    exports.getByID = (id) =>
    new Promise((resolve, reject) => {
    
        buatseminarModel.findOne({
          _id: ObjectId(id)
        })
          .then(res => {
            resolve({
              error: false,
              pesan: 'Berhasil Mengambil Data',
              data: res
            })
               })
                
          .catch(() => {
            reject({
              error: true,
              pesan: 'Gagal Mengambil Data'
            })
          })
        })
  
        exports.editseminar = (id, data) =>                                 
        new Promise ((resolve,reject) => {
          buatseminarModel.update({
            _id: ObjectId(id)
          }, data).then((res) => {
            console.log(res)
            resolve({
              error: false,
              pesan: 'Berhasil Mengubah Data',
              data: res
            })
          }).catch(() => {
            reject({
              error: true,
              pesan: 'Gagal Mengubah data Data'
            })
          })
        })
  
      exports.deleteseminar = (id) => 
      new Promise ((resolve,reject) => {
        buatseminarModel.deleteOne({
          _id: ObjectId(id)
        }).then(() => {
          resolve({
            error: false,
            pesan: 'Berhasil Menghapus Data'
          })
        }).catch(() => {
          reject({
            error: true,
            pesan: 'Data tidak terhapus'
          })
        })
      })