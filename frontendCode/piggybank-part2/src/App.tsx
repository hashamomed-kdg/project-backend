import './App.css'
import {BrowserRouter, Navigate, Route, Routes} from "react-router"
import {PiggyBankDetailPage} from "./pages/PiggyBankDetailPage.tsx"
import {PiggyBankListPage} from "./pages/PiggyBankListPage.tsx"
import axios from "axios"
import {QueryClient, QueryClientProvider} from "@tanstack/react-query"

axios.defaults.baseURL = 'http://localhost:3001'
const queryClient = new QueryClient()

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <Routes>
                    <Route path="/piggybanks/:id" element={<PiggyBankDetailPage/>}/>
                    <Route path="/piggybanks" element={<PiggyBankListPage/>}/>
                    <Route path="/" element={<Navigate to="/piggybanks"/>}/>
                </Routes>
            </BrowserRouter>
        </QueryClientProvider>
    )
}

export default App
