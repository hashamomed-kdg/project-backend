import './PiggyBankView.scss'
import type {PiggyBank} from "../model/piggyBank"
import {OwnerBadge} from "./OwnerBadge"
import {useEffect, useState} from "react"
import {type Background, BACKGROUNDS} from "../model/background"

interface PiggyBankViewProps {
    piggyBank: PiggyBank
    size?: "large" | "small"
    onBackgroundUpdated: (background: Background) => void
}

function cycleBackgroundValue(current: Background, step: number): Background {
    const i = Math.max(0, BACKGROUNDS.indexOf(current))
    const next = (i + step + BACKGROUNDS.length) % BACKGROUNDS.length
    return BACKGROUNDS[next]
}

export function PiggyBankView({
                                  piggyBank,
                                  size = "large",
                                  onBackgroundUpdated,
                              }: PiggyBankViewProps) {
    const isLarge = size === "large"

    // only UI state
    const [showFields, setShowFields] = useState(false)

    const background = piggyBank.background

    useEffect(() => {
        if (!showFields) return
        const onKeyDown = (e: KeyboardEvent) => {
            if (e.key === "Escape") setShowFields(false)
        }
        window.addEventListener("keydown", onKeyDown)
        return () => window.removeEventListener("keydown", onKeyDown)
    }, [showFields])

    const goPrev = () => onBackgroundUpdated(cycleBackgroundValue(background, -1))
    const goNext = () => onBackgroundUpdated(cycleBackgroundValue(background, +1))

    return (
        <div className={["piggy-bank", size, `bg-${background}`].join(" ")}>
            {isLarge && (
                <div className="bg-controls">
                    <button
                        type="button"
                        className="bg-btn"
                        onClick={goPrev}
                        aria-label="Previous theme"
                    >
                        ◀
                    </button>
                    <button
                        type="button"
                        className="bg-btn"
                        onClick={goNext}
                        aria-label="Next theme"
                    >
                        ▶
                    </button>
                </div>
            )}

            <OwnerBadge
                owner={piggyBank.owner}
                size={size}
                onClick={() => {
                    if (isLarge) setShowFields(s => !s)
                }}
            />

            {isLarge && showFields && (
                <div className="fields">
                    <div className="owner">{piggyBank.owner.name}</div>
                    <div className="balance">{piggyBank.amount}</div>
                </div>
            )}
        </div>
    )
}
