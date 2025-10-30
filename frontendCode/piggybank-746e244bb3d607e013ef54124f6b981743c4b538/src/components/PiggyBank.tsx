import './PiggyBank.scss'
import type {Account} from "../model/Acount.ts"
import {OwnerBadge} from "./OwnerBadge.tsx"
import {useState} from "react"

interface PiggyBankProps {
    account: Account
}

const backgrounds = ["classic", "theo", "race", "unicorn"] as const

export function PiggyBank({account}: PiggyBankProps) {
    const [showFields, setShowFields] = useState(false)
    const [currentIndex, setCurrentIndex] = useState(0)

    const changeBackgroundIndex = (step: number) => {
        setCurrentIndex(i => (i + step + backgrounds.length) % backgrounds.length)
    }
    const goPrev = () => changeBackgroundIndex(-1)
    const goNext = () => changeBackgroundIndex(1)

    const background = backgrounds[currentIndex]

    return (
        <div className={`piggy-bank bg-${background}`}>
            <div className="bg-controls">
                <button type="button" className="bg-btn" onClick={goPrev} aria-label="Previous theme">◀</button>
                <button type="button" className="bg-btn" onClick={goNext} aria-label="Next theme">▶</button>
            </div>

            <OwnerBadge
                owner={account.owner}
                onClick={() => setShowFields(s => !s)}
                aria-expanded={showFields}
            />

            {showFields && (
                <div className="fields">
                    <div className="owner">{account.owner.name}</div>
                    <div className="balance">{account.balance}</div>
                </div>
            )}
        </div>
    )
}
