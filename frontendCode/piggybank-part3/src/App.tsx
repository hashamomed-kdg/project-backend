import './App.css'
import {BrowserRouter, Navigate, Route, Routes} from "react-router"
import {PiggyBankDetailPage} from "./pages/PiggyBankDetailPage.tsx"
import {PiggyBankListPage} from "./pages/PiggyBankListPage.tsx"
import {Navigation} from "./components/Navigation.tsx"
import axios from "axios"
import {QueryClient, QueryClientProvider} from "@tanstack/react-query"
import About from "./components/About.tsx"
import {useState} from "react"
import {Header} from "./components/Header.tsx"
import {CssBaseline, ThemeProvider} from "@mui/material"
import {theme} from "./theme/theme.ts"

axios.defaults.baseURL = 'http://localhost:3001'
const queryClient = new QueryClient()

function App() {
    const [isNavigationOpen, setIsNavigationOpen] = useState(false)
    return (
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <ThemeProvider theme={theme}>
                    <CssBaseline/>
                    <Header onMenuClick={() => setIsNavigationOpen(true)}/>
                    <Navigation isOpen={isNavigationOpen} onClose={() => setIsNavigationOpen(false)}/>
                    <Routes>
                        <Route path="/about" element={<About/>}/>
                        <Route path="/piggybanks/:id" element={<PiggyBankDetailPage/>}/>
                        <Route path="/piggybanks" element={<PiggyBankListPage/>}/>
                        <Route path="/" element={<Navigate to="/piggybanks"/>}/>
                    </Routes>
                </ThemeProvider>
            </BrowserRouter>
        </QueryClientProvider>
    )
}

export default App
