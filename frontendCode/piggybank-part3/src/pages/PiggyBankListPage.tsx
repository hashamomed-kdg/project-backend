import {useState} from "react"
import {usePiggyBanks} from "../hooks/usePiggyBanks.ts"
import {Link} from "react-router"
import {PiggyBankView} from "../components/PiggyBankView.tsx"
import {OwnerCard} from "../components/OwnerCard.tsx"
import {Box, ToggleButton, ToggleButtonGroup} from "@mui/material"
import SavingsIcon from '@mui/icons-material/Savings'
import PersonIcon from "@mui/icons-material/Person"

export function PiggyBankListPage() {
    const {piggyBanks, isLoading, isError} = usePiggyBanks()
    const [viewMode, setViewMode] = useState<"piggybank" | "owner">("piggybank")

    if (isLoading) return <div>Loading...</div>
    if (isError || !piggyBanks) return <div>Error!</div>

    return (
        <Box display="flex" flexDirection="column" alignItems="center" gap={8}>
            <ToggleButtonGroup
                value={viewMode}
                exclusive
                onChange={(_, newMode) => newMode && setViewMode(newMode)}
                aria-label="view toggle"
            >
                <ToggleButton value="piggybank" aria-label="Piggy Bank View">
                    <SavingsIcon/>
                </ToggleButton>
                <ToggleButton value="owner" aria-label="Owner View">
                    <PersonIcon/>
                </ToggleButton>
            </ToggleButtonGroup>

            <Box
                display="flex"
                justifyContent="center"
                gap={4}
                flexWrap="wrap"
                sx={{minHeight: 440}}
            >
                {piggyBanks.map((piggyBank) => (
                    <Link
                        key={piggyBank.id}
                        to={`/piggybanks/${piggyBank.id}`}
                        style={{textDecoration: "none"}}
                    >
                        {viewMode === "piggybank" ? (
                            <PiggyBankView
                                piggyBank={piggyBank}
                                size="small"
                                onBackgroundUpdated={() => {
                                }}
                            />
                        ) : (
                            <OwnerCard owner={piggyBank.owner}/>
                        )}
                    </Link>
                ))}
            </Box>
        </Box>
    )
}
