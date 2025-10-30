import {usePiggyBanks} from "../hooks/usePiggyBanks.ts"
import {Link} from "react-router"
import {PiggyBankView} from "../components/PiggyBankView.tsx"

export function PiggyBankListPage() {
    const {piggyBanks, isLoading, isError} = usePiggyBanks()

    if (isLoading) {
        return <div>Loading...</div>
    }

    if (isError || !piggyBanks) {
        return <div>Error!</div>
    }

    return (
        <div style={{display: "flex", justifyContent: "center", gap: "2rem", flexWrap: "wrap"}}>
            {piggyBanks.map((piggyBank) =>
                <Link to={`/piggybanks/${piggyBank.id}`} key={piggyBank.id} style={{textDecoration: 'none'}}>
                    <PiggyBankView piggyBank={piggyBank} size="small" onBackgroundUpdated={() => {
                    }}/>
                </Link>)}
        </div>


    )
}