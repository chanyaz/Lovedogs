var mongoose = require("mongoose");

// create instance of Schema
var mongoSchema = mongoose.Schema;

var PetshopSchema = new mongoose.Schema({
    name: String,
    address: String,
    description: String,
    phone: String,
    latitude: Number,
    longitude: Number,
    number_of_customers: String,
    favorite: Boolean,
    since: String,
    image_url: String,
    open: String,
    close:String,
    stars: Number,
    updated_at: {type: Date, default: Date.now},
});

module.exports = mongoose.model('Petshop', PetshopSchema);