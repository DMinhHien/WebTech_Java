import { ProductCart } from "../data/Cart";
import { RawOrderItem,OrderUser,OrderDetail } from "../data/order";
import axios from "axios";
export const getListOrder= async(id:string) =>{
    try 
    {
        const res=await axios.get<RawOrderItem[]>(`http://localhost:8080/Receipt/getListUseShop/${id}`)
        return res.data.data;
    } catch (error) {
        console.error("không thể lấy danh sách đơn hàng",error);
        throw error
    }
}
export const getListOrderUser= async(id:string) =>{
    try 
    {
        const res=await axios.get<OrderUser[]>(`http://localhost:8080/Receipt/getListUse/${id}`)
        return res.data.data;
    } catch (error) {
        console.error("không thể lấy danh sách đơn hàng",error);
        throw error
    }
}
export const getListOrderDetail= async(id:string) =>{
    try 
    {
        const res=await axios.get<OrderDetail[]>(`http://localhost:8080/Receipt/getReceiptDetail/${id}`)
        return res.data.data;
    } catch (error) {
        console.error("không thể lấy danh sách đơn hàng",error);
        throw error
    }
}

export const createReceipt=async(userId:string,ProductList:ProductCart[])=>{
    try {
        const token=localStorage.getItem("token")
        console.log("ProductList",ProductList)
        const res=await axios.post(`http://localhost:8080/Receipt/create/${userId}`,ProductList,{ headers: { Authorization: `Bearer ${token}` } }) 
        return res.data.data
    } catch (error) {
        console.error("không thể tạo đơn hàng",error);
        throw error
    }
}

export const getReceiptDetail=async(id:string)=>{
    try {
        const res=await axios.get(`http://localhost:8080/Receipt/getReceiptDetail/${id}`) 
        return res.data.data
    } catch (error) {
        console.error("không thể lấy danh sách ReceiptDetail",error);
        throw error
    }
}