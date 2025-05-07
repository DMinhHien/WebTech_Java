import axios from "axios";
import { Product } from "../data/productdetail";

export const addProduct =async(Product:Product)=>{
    try {
        const { id, ...productWithoutId } = Product;    
        const token = localStorage.getItem("token")
        await axios.post(`http://localhost:8080/Product/create`,{productWithoutId },{ headers: { Authorization: `Bearer ${token}` } })
    } catch (error) {
        console.error("không thể thêm sản phẩm",error);
        throw error
    }
}

export const editProduct=async(Product:Product)=>{
    try {
        const token=localStorage.getItem("token")
        await axios.post(`http://localhost:8080/Product/edit`,Product ,{ headers: {Authorization:`Bearer ${token}`} })
    } catch (error) {
        console.error("không thể chỉnh sửa sản phẩm",error);
        throw error
    }
}

export const deleteProduct=async(id:string)=>{
    try {
        const token=localStorage.getItem("token")
        const res=await axios.post(`http://localhost:8080/Product/delete`,{id},{headers:{Authorization:`Bearer ${token}`}})
        return res.data
    } catch (error) {
        console.error("không thể xóa sản phẩm",error);
        throw error
    }
}

export const getListProduct= async(id:string) =>{
    try 
    {
        const res=await axios.get<Product[]>(`http://localhost:8080/Product/getListUseShop/${id}`)
        return res.data.data;
    } catch (error) {
        console.error("không thể lấy danh sách sản phẩm",error);
        throw error
    }
}

export const getProduct=async(id:string)=>{
    try {
        const res= await axios.get<Product>(`http://localhost:8080/Product/getElementById/${id}`)
        return res.data.data
    } catch (error) {
        throw error
    }
}