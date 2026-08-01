<#
.SYNOPSIS
    Scaffolds a new LeetCode problem folder.

.EXAMPLE
    .\new-problem.ps1 -Number 217 -Title "Contains Duplicate" -Difficulty Easy -Topics "Array, Hash Table"
#>
param(
    [Parameter(Mandatory = $true)][int]$Number,
    [Parameter(Mandatory = $true)][string]$Title,
    [ValidateSet('Easy', 'Medium', 'Hard')][string]$Difficulty = 'Easy',
    [string]$Topics = ''
)

$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot

# "Contains Duplicate" -> "contains-duplicate"
$slug = ($Title.ToLower() -replace "[^a-z0-9]+", "-").Trim('-')
$padded = '{0:D4}' -f $Number
$folder = Join-Path $root "$padded-$slug"

if (Test-Path $folder) {
    Write-Host "Already exists: $padded-$slug" -ForegroundColor Yellow
    return
}

New-Item -ItemType Directory -Path $folder | Out-Null

@"
class Solution {
    // TODO: paste your accepted solution here
}
"@ | Out-File (Join-Path $folder 'Solution.java') -Encoding utf8

@"
# $Number. $Title

**Difficulty:** $Difficulty
**Topics:** $Topics
**Link:** https://leetcode.com/problems/$slug/

## Problem

TODO

## Approach

TODO

## Complexity

- **Time:** O(?)
- **Space:** O(?)
"@ | Out-File (Join-Path $folder 'README.md') -Encoding utf8

# Add a row to the progress table in the root README
$readme = Join-Path $root 'README.md'
$topicText = if ($Topics) { $Topics } else { '-' }
$row = "| $Number | [$Title](https://leetcode.com/problems/$slug/) | $Difficulty | $topicText | [Java](./$padded-$slug/Solution.java) |"

$lines = [System.Collections.Generic.List[string]](Get-Content $readme)
$lastRow = ($lines | Select-String -Pattern '^\| \d+ \|' | Select-Object -Last 1).LineNumber
if ($lastRow) {
    $lines.Insert($lastRow, $row)

    # Bump the solved counter to match the number of table rows
    $count = ($lines | Select-String -Pattern '^\| \d+ \|').Count
    for ($i = 0; $i -lt $lines.Count; $i++) {
        if ($lines[$i] -match '^\*\*Total solved:\*\*') {
            $lines[$i] = "**Total solved:** $count"
            break
        }
    }
    $lines | Out-File $readme -Encoding utf8
} else {
    Write-Host "Couldn't find the progress table - add the row manually:" -ForegroundColor Yellow
    Write-Host $row
}

Write-Host "Created $padded-$slug" -ForegroundColor Green
Write-Host "  -> $folder\Solution.java"
