const pesertaseminarModel = require('../models/pesertaseminar')
const ObjectId = require ('mongoose').Types.ObjectId

exports.insertpeserta = (data) =>
  new Promise((resolve, reject) => {
    pesertaseminarModel.create(data)
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

  exports.getAllpeserta = () =>
  new Promise((resolve, reject) => {
    pesertaseminarModel.find()
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
    
        pesertaseminarModel.findOne({
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
  
    exports.editpeserta = (id, data) =>                                 
      new Promise ((resolve,reject) => {
        pesertaseminarModel.update({
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
  
  
  
  
      exports.deletepeserta = (id) => 
      new Promise ((resolve,reject) => {
        pesertaseminarModel.deleteOne({
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