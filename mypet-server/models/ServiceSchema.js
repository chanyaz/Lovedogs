var mongoose = require("mongoose");

// create instance of Schema
var mongoSchema = mongoose.Schema;

var ServiceSchema = new mongoose.Schema({
    id: String,
    id_petshop: String,
    name: String,
    image_service: String,
    value: Number,
    updated_at: {type: Date, default: Date.now},
});

module.exports = mongoose.model('Service', ServiceSchema);