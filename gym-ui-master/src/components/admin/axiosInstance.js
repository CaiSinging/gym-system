import axios from 'axios';
import Configuration from "../../config";

const axiosInstance = axios.create({
    baseURL: Configuration.API_ENDPOINT,
    headers: {
        'Authorization': {
            toString() {
                return 'Bearer ' + sessionStorage.getItem("adminToken");
            }
        }
    }
});
export default axiosInstance;